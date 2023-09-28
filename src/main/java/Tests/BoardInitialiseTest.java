package Tests;

import com.rossw01.chess.Board.ChessBoard;
import com.rossw01.chess.Game.ChessIntialiser;
import com.rossw01.chess.Game.ChessLayout;

public class BoardInitialiseTest {
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard();
        ChessIntialiser.initialiseBoard(chessBoard, ChessLayout.standard);

        int[] backrankLayers = new int[]{1, 8};
        for (int y : backrankLayers) {
            for (int x = 1; x <= chessBoard.X_WIDTH; x++) {
                System.out.println("Initialised piece of type '" + ChessLayout.standard[x - 1].toString() + "' at boardPos: " + String.valueOf(x) + ", " + String.valueOf(y));
                if (chessBoard.getPieceAt(x, y).getPieceType().toString().equals(ChessLayout.standard[x - 1].toString())) {
                    System.out.println(("Board position " + String.valueOf(x)) + ", " + String.valueOf(y) + " should be " + ChessLayout.standard[x - 1].toString() + " and is: " + chessBoard.getPieceAt(x, y).getPieceType().toString() + " \033[42m" + " Passed! " + "\033[0m");
                } else {
                    System.out.println(("Board position " + String.valueOf(x)) + ", " + String.valueOf(y) + " should be " + ChessLayout.standard[x - 1].toString() + " and is: " + chessBoard.getPieceAt(x, y).getPieceType().toString() + " \033[41m" + " Failed! " + "\033[0m");
                }
            }
        }
    }
}
