package com.rossw01.chess.Game;

import com.rossw01.chess.Board.ChessBoard;
import com.rossw01.chess.Pieces.Colour;
import com.rossw01.chess.Pieces.*;

public class ChessIntialiser {
    public static void initialiseBoard(ChessBoard chessBoard, ChessPieces[] pieceLayout) {
        int[] pawnLayers = new int[]{2,7};
        int[] backLayers = new int[]{1,8};

        // Pawn setup
        for (int y : pawnLayers) {
            for(int x = 1; x <= chessBoard.X_WIDTH; x++) {
                chessBoard.setPieceAt(x, y, new Pawn(x, y, y == 2 ? Colour.WHITE : Colour.BLACK));
            }
        }

        // Backrank setup
        for (int y : backLayers) {
            for(int x = 0; x < chessBoard.X_WIDTH; x++) {
                if (pieceLayout[x] == ChessPieces.ROOK) chessBoard.setPieceAt(x + 1, y, new Rook(x + 1, y, y == 1 ? Colour.WHITE : Colour.BLACK));
                else if (pieceLayout[x] == ChessPieces.KNIGHT) chessBoard.setPieceAt(x + 1, y, new Knight(x + 1, y, y == 1 ? Colour.WHITE : Colour.BLACK));
                else if (pieceLayout[x] == ChessPieces.BISHOP) chessBoard.setPieceAt(x + 1, y, new Bishop(x + 1, y, y == 1 ? Colour.WHITE : Colour.BLACK));
                else if (pieceLayout[x] == ChessPieces.KING) chessBoard.setPieceAt(x + 1, y, new King(x + 1, y, y == 1 ? Colour.WHITE : Colour.BLACK));
                else if (pieceLayout[x] == ChessPieces.QUEEN) chessBoard.setPieceAt(x + 1, y, new Queen(x + 1, y, y == 1 ? Colour.WHITE : Colour.BLACK));
                else if (pieceLayout[x] == ChessPieces.PAWN) chessBoard.setPieceAt(x + 1, y, new Pawn(x + 1, y, y == 1 ? Colour.WHITE : Colour.BLACK));
            }
        }
    }
}
