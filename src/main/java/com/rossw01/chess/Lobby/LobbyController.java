package com.rossw01.chess.Lobby;

import com.rossw01.chess.Network.ClientWebSocketController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.ConnectException;
import java.util.Objects;
import java.util.Optional;

public class LobbyController {
    private ClientWebSocketController clientController;
    @FXML
    private TextField nameField;
    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;
    @FXML
    private ListView<String> gameList;

    private String myName;

    public void setClientController(ClientWebSocketController client) throws InterruptedException, ConnectException {
        this.clientController = client;

        getGames();
    }

    @FXML
    private void onJoinGameButtonClick() {
        if (gameList.getSelectionModel().getSelectedItem() == null) {
            Alert noGameSelectionAlert = new Alert(Alert.AlertType.ERROR,"You must first select a game from the game list and then click the join button. Alternatively, you can create your own game.");
            noGameSelectionAlert.setHeaderText("No game was selected.");
            noGameSelectionAlert.showAndWait();
            return;
        }
        if (isDisconnected()) {
            return;
        }
        if (myName == null) {
            Alert noUsernameError = new Alert(Alert.AlertType.ERROR, "You must set a username before creating a game. Please set a username in the top right of the client.");
            noUsernameError.setHeaderText("No username has been set");
            noUsernameError.showAndWait();
            return;
        }
    }
    @FXML
    private void onCreateGameButtonClick() {
        if (isDisconnected()) {
            return;
        }

        if (myName == null || Objects.equals(myName, "")) {
            Alert noUsernameError = new Alert(Alert.AlertType.ERROR, "You must set a username before creating a game. Please set a username in the top right of the client.");
            noUsernameError.setHeaderText("No username has been set");
            noUsernameError.showAndWait();
            return;
        }

        TextInputDialog gameNameInput = new TextInputDialog(String.format("%s's Chess Game", myName));
        gameNameInput.setTitle("Create a game");
        gameNameInput.setHeaderText("Input the name for your game. Other users will see this in the game list.");

        Optional<String> gameName = gameNameInput.showAndWait();

        if (!gameName.isPresent()) return;
        if (gameName.get().length() == 0) {
            Alert noGameNameAlert = new Alert(Alert.AlertType.WARNING, "No game name was inputted. Your game has not been created, please try again.");
            noGameNameAlert.showAndWait();
            return;
        }

        clientController.createRoom(gameName.get());
    }
    @FXML
    private void getGames() throws InterruptedException {
        // Since this is called on startup, isDisconnected shouldn't be called
        if (clientController.getClient().isOpen()) {
            gameList.getItems().clear();
            gameList.getItems().addAll(clientController.getRooms());
        }
    }

    @FXML
    private void setName() {
        if (isDisconnected()) {
            return;
        }

        String nameFieldContents = nameField.getText().trim();
        if (nameFieldContents.length() == 0) {
            return;
        }

        clientController.setName(nameFieldContents);
        myName = nameFieldContents;

        nameField.setText("");
    }

    private boolean isDisconnected() {
        if (!clientController.getClient().isOpen()) {
            Alert clientDisconnectedAlert = new Alert(Alert.AlertType.ERROR, "You are not connected to the game server. Please restart the client and try again.");
            clientDisconnectedAlert.showAndWait();
            return true;
        }
        return false;
    }
}
