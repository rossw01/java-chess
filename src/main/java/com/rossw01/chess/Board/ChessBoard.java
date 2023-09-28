package com.rossw01.chess.Board;

import com.rossw01.chess.Pieces.ChessPiece;
import com.rossw01.chess.Pieces.Colour;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    public final int X_WIDTH = 8;
    public final int Y_WIDTH = 8;

    private final Tile[][] board;

    private final List<ChessPiece> whitePieces = new ArrayList<>();
    private final List<ChessPiece> blackPieces = new ArrayList<>();

    public ChessBoard(ChessBoard originalBoard) {
        int counter = 0;
        this.board = new Tile[X_WIDTH][Y_WIDTH];
        for (int i = 0; i < X_WIDTH; i++) {
            for (int j = 0; j < Y_WIDTH; j++) {
                this.board[i][j] = new Tile(counter % 2 == 0 ? Colour.BLACK : Colour.WHITE);
                this.board[i][j].setPiece(originalBoard.getPieceAt(i + 1, Y_WIDTH - j));
                counter++;
            }
        }
        this.whitePieces.addAll(originalBoard.getWhitePieces());
        this.blackPieces.addAll(originalBoard.getBlackPieces());
    }
    public List<ChessPiece> getWhitePieces() {
        return whitePieces;
    }

    public List<ChessPiece> getBlackPieces() {
        return blackPieces;
    }
    public Tile[][] getBoard() {
        return board;
    }
    public ChessBoard() {
        int counter = 0;
        this.board = new Tile[X_WIDTH][Y_WIDTH];
        for (int i = 0; i < X_WIDTH; i++) {
            for (int j = 0; j < Y_WIDTH; j++) {
                board[i][j] = new Tile(counter % 2 == 0 ? Colour.BLACK : Colour.WHITE);
                counter++;
            }
        }
    }
    public ChessPiece getPieceAt(int posX, int posY) {

        return board[posX - 1][Y_WIDTH - posY].getPiece();
    }

    public void setPieceAt(int posX, int posY, ChessPiece piece) {
        ChessPiece pieceAtLocation = getPieceAt(posX, posY);
        // If a piece is taken with setPieceAt, remove from whitePieces/blackPieces

        if (pieceAtLocation != null) {
            if (pieceAtLocation.getColour() == Colour.WHITE) whitePieces.remove(pieceAtLocation);
            else if (pieceAtLocation.getColour() == Colour.BLACK) blackPieces.remove(pieceAtLocation);
        }

        if (piece.getColour() == Colour.BLACK) blackPieces.add(piece);
        if (piece.getColour() == Colour.WHITE) whitePieces.add(piece);
        board[posX - 1][Y_WIDTH - posY].setPiece(piece);
    }

    public void removePieceAt(int posX, int posY, ChessPiece piece) {
        Colour pieceColour = piece.getColour();
        if (pieceColour == Colour.BLACK) {
            blackPieces.remove(piece);
        }
        if (pieceColour == Colour.WHITE) {
            whitePieces.remove(piece);
        }
        board[posX - 1][Y_WIDTH - posY].setPiece(null);
    }
}
