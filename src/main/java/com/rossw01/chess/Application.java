package com.rossw01.chess;

import com.rossw01.chess.Board.ChessBoard;
import com.rossw01.chess.Game.ChessIntialiser;
import com.rossw01.chess.Game.ChessLayout;
import com.rossw01.chess.Lobby.LobbyController;
import com.rossw01.chess.Network.ClientWebSocketController;
import com.rossw01.chess.Pieces.ChessPiece;
import com.rossw01.chess.Pieces.Colour;
import com.rossw01.chess.Widgets.ReconnectAlert;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Application extends javafx.application.Application {

    private final int TILE_SIZE = 60;
    private final ChessBoard chessBoard = new ChessBoard();
    private int[] selectedTile = new int[]{};
    private GridPane board = new GridPane();
    private Colour currentTurn = Colour.WHITE;

    public int[] getSelectedTile() {
        return selectedTile;
    }
    public void setSelectedTile(int[] selectedTile) {
        this.selectedTile = selectedTile;
        drawBoard(board);
    }
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, InterruptedException {
        boolean isConnected = false;
        ClientWebSocketController client = new ClientWebSocketController();

        client.initialiseConnection();

//        TODO: Diconnected check
//        Alert reconnectAlert = new ReconnectAlert();
//        reconnectAlert.showAndWait().ifPresent(buttonPressed -> {
//            String reconnectResponse = buttonPressed.getText();
//            if (Objects.equals(reconnectResponse, "Quit")) {
//                Platform.exit();
//                System.exit(0);
//            } else if (Objects.equals(reconnectResponse, "Reconnect")) {
//                client.reconnect();
//            } else if (Objects.equals(reconnectResponse, "Continue Offline")) {
//                // TODO: Continue Offline
//            }
//        });

        initialiseLobbyScene(stage, client);
//        initialiseChessScene(stage);
    }

    public void initialiseLobbyScene(Stage stage, ClientWebSocketController client) throws IOException, InterruptedException {
        FXMLLoader lobbyLoader = new FXMLLoader(getClass().getResource("lobby.fxml"));
        Parent lobby = lobbyLoader.load();
        LobbyController lobbyController = lobbyLoader.<LobbyController>getController();

        lobbyController.setClientController(client);

        Pane root = new Pane();
        root.getChildren().addAll(lobby);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chess Lobby");
        stage.show();
    }

    // TODO: Refactor these game logic functions into own Class
    public void initialiseChessScene(Stage stage) throws IOException{
        int PADDING_SIZE = TILE_SIZE / 5;

        FXMLLoader chatLoader = new FXMLLoader(Application.class.getResource("chat.fxml"));
        Parent chat = chatLoader.load();

        ChessIntialiser.initialiseBoard(chessBoard, ChessLayout.standard);
        board = drawBoard(board);
        board.setPadding(new Insets(PADDING_SIZE));

        HBox root = new HBox();
        root.getChildren().addAll(board, chat);

        Scene scene = new Scene(root, ((TILE_SIZE * chessBoard.X_WIDTH) + PADDING_SIZE * 3) + chat.minWidth(0), (TILE_SIZE * chessBoard.Y_WIDTH) + PADDING_SIZE * 2);
        scene.setFill(Color.LIGHTGRAY);

        stage.setScene(scene);
        stage.setTitle("Chessboard");
        stage.show();
    }

    public GridPane drawBoard(GridPane board) {
        for (int row = 0; row < chessBoard.X_WIDTH; row++) {
            for (int col = 0; col < chessBoard.Y_WIDTH; col++) {

                StackPane tileContainer = new StackPane();
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                Color tileColour =  (row + col) % 2 == 0 ? Color.WHITE : Color.GREEN;

                if (selectedTile.length != 0 && col + 1 == selectedTile[0] && chessBoard.Y_WIDTH - row == selectedTile[1]) {
                    tile.setFill(Color.hsb(tileColour.getHue(), tileColour.getSaturation(), tileColour.getBrightness() * 0.8));
                } else {
                    tile.setFill(tileColour);
                }

                int finalCol = col;
                int finalRow = row;

                tileContainer.getChildren().addAll(tile);

                if (selectedTile.length != 0) {
                    ChessPiece piece = chessBoard.getPieceAt(selectedTile[0], selectedTile[1]);
                    if (piece.getColour().equals(currentTurn) && piece.isValidMove(piece, finalCol, finalRow, chessBoard)) {
                        Circle circle = new Circle((double) TILE_SIZE / 5);
                        circle.setFill(Color.hsb(tileColour.getHue(), tileColour.getSaturation(), tileColour.getBrightness() * 0.8));
                        tileContainer.getChildren().addAll(circle);
                    }
                }

                ChessPiece piece = chessBoard.getPieceAt(col + 1, chessBoard.Y_WIDTH - row);

                if (piece != null) {
                    URL url = getClass().getResource("/ChessPieceIcons/" + piece.getPieceType().toString() + "_" + piece.getColour().toString() + ".png");
                    ImageView pieceImageView = new ImageView(String.valueOf(url));
                    tileContainer.getChildren().addAll(pieceImageView);
                }

                tileContainer.setOnMouseClicked((MouseEvent event) -> tileClickHandler(chessBoard, finalCol + 1, chessBoard.Y_WIDTH - finalRow));
                board.add(tileContainer, col, row);
            }
        }
        return board;
    }

    private void playSound(String sound) {
        String turnSoundPath = null;
        if (Objects.equals(sound, "move")) {
            turnSoundPath = Objects.requireNonNull(getClass().getResource("/Sounds/chesspiecemove.mp3")).toExternalForm();
        } else if (Objects.equals(sound, "capture")) {
            turnSoundPath = Objects.requireNonNull(getClass().getResource("/Sounds/chesspiececapture.mp3")).toExternalForm();
        }

        if (turnSoundPath == null) {
            return;
        }
        Media media = new Media(turnSoundPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    private void movePiece(int x, int y, ChessPiece piece) {
        if (piece.getColour() != currentTurn) {
            return;
        }
        chessBoard.setPieceAt(x, y, piece);
        chessBoard.removePieceAt(getSelectedTile()[0], getSelectedTile()[1], piece);
        setSelectedTile(new int[]{});
        currentTurn = currentTurn.equals(Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
    }

    private void tileClickHandler(ChessBoard chessBoard, int x, int y) {
        ChessPiece piece = chessBoard.getPieceAt(x, y);

        if (selectedTile.length == 0 && piece == null) {
            return;
        }

        if (selectedTile.length == 0) {
            setSelectedTile(new int[]{x, y});
            return;
        }

        if (piece == null && getSelectedTile().length == 0) {
            return;
        }

        if (piece == null && getSelectedTile().length > 0) {
            ChessPiece pieceToMove = chessBoard.getPieceAt(selectedTile[0], selectedTile[1]);
            if (pieceToMove.getColour() == currentTurn && pieceToMove.move(x, y, chessBoard)) {
                playSound("move");
                movePiece(x, y, pieceToMove);
            } else {
                setSelectedTile(new int[]{});
            }
            return;
        }

        if (piece != null && piece.getPosX() == getSelectedTile()[0] && piece.getPosY() == getSelectedTile()[1]) {
            setSelectedTile(new int[]{});
            return;
        }

        assert piece != null;
        if (piece.getColour().equals(currentTurn)) {
            setSelectedTile(new int[]{x, y});
            return;
        }

        ChessPiece pieceToMove = chessBoard.getPieceAt(selectedTile[0], selectedTile[1]);
        if (pieceToMove.getColour() == currentTurn && pieceToMove.move(x, y, chessBoard)) {
            playSound("capture");
            movePiece(x, y, pieceToMove);
        } else {
            setSelectedTile(new int[]{});
        }
    }

    public static void main(String[] args) {
        launch();
    }
}