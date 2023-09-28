package com.rossw01.chess.Pieces;

import com.rossw01.chess.Board.ChessBoard;

public class Knight extends ChessPiece {
    public Knight(int posX, int posY, Colour colour) {
        super(ChessPieces.KNIGHT);
        setPosX(posX);
        setPosY(posY);
        setColour(colour);
    }

    @Override
    public boolean validateMove(int newPosX, int newPosY, ChessBoard board) {
        int xDiff = Math.abs(newPosX - getPosX());
        int yDiff = Math.abs(newPosY - getPosY());

        if (Math.max(xDiff, yDiff) != 2 && Math.min(xDiff, yDiff) != 1) {
            return false;
        }
        if (xDiff == 0 || yDiff == 0 || yDiff == xDiff || xDiff > 2 || yDiff > 2) {
            return false;
        }
        return true;
    }
    @Override
    public boolean move(int newPosX, int newPosY, ChessBoard board) {
        return validateMove(newPosX, newPosY, board) && super.move(newPosX, newPosY, board);
    }
}
