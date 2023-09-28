package com.rossw01.chess.Pieces;

import com.rossw01.chess.Board.ChessBoard;

public class Bishop extends ChessPiece {
    public Bishop(int posX, int posY, Colour colour) {
        super(ChessPieces.BISHOP);
        setPosX(posX);
        setPosY(posY);
        setColour(colour);
    }
    @Override
    public boolean validateMove(int newPosX, int newPosY, ChessBoard board) {
        return Math.abs(newPosX - getPosX()) == Math.abs(newPosY - getPosY());
    }
    @Override
    public boolean move(int newPosX, int newPosY, ChessBoard board) {
        return validateMove(newPosX, newPosY, board) && super.move(newPosX, newPosY, board);
    }
}
