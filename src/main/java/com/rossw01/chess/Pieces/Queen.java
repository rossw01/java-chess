package com.rossw01.chess.Pieces;

import com.rossw01.chess.Board.ChessBoard;

public class Queen extends ChessPiece {
    public Queen(int posX, int posY, Colour colour) {
        super(ChessPieces.QUEEN);
        setPosX(posX);
        setPosY(posY);
        setColour(colour);
    }

    @Override
    public boolean validateMove(int newPosX, int newPosY, ChessBoard board) {
        int xDiff = Math.abs(newPosX - getPosX());
        int yDiff = Math.abs(newPosY - getPosY());

        return (xDiff <= 0 || yDiff <= 0) || (xDiff == yDiff);
    }
    @Override
    public boolean move(int newPosX, int newPosY, ChessBoard board) {
        return validateMove(newPosX, newPosY, board) && super.move(newPosX, newPosY, board);
    }
}
