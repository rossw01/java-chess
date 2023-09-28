package com.rossw01.chess.Pieces;

import com.rossw01.chess.Board.ChessBoard;

public class King extends ChessPiece {
    private boolean isChecked;
    public King(int posX, int posY, Colour colour) {
        super(ChessPieces.KING);
        setPosX(posX);
        setPosY(posY);
        setColour(colour);
    }

    @Override
    public boolean validateMove(int newPosX, int newPosY, ChessBoard board) {
        // TODO: isCheck
        return !(Math.abs(newPosX - getPosX()) > 1 || Math.abs(newPosY - getPosY()) > 1);
    }
    @Override
    public boolean move(int newPosX, int newPosY, ChessBoard board) {
        return  validateMove(newPosX, newPosY, board) && super.move(newPosX, newPosY, board);
    }
}
