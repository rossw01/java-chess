package com.rossw01.chess.Pieces;

import com.rossw01.chess.Board.ChessBoard;

public class Pawn extends ChessPiece {
    private boolean isMoved;
    public Pawn(int posX, int posY, Colour colour) {
        super(ChessPieces.PAWN);
        setPosX(posX);
        setPosY(posY);
        setColour(colour);
        setMoved(false);
    }
    public void setMoved(boolean moved) {
        isMoved = moved;
    }
    @Override
    public boolean validateMove(int newPosX, int newPosY, ChessBoard board) {
        int xDiff = Math.abs(Math.max(newPosX, getPosX()) -  Math.min(newPosX, getPosX()));
        if (xDiff > 1) {
            return false;
        }

        if (xDiff == 1 && newPosY == getPosY()) return false;

        if (getColour() == Colour.WHITE) {
            if (!(newPosY == getPosY() + 1 || (newPosY == getPosY() + 2 && !isMoved && board.getPieceAt(newPosX, newPosY) == null))) {
                return false;
            }
        } else if (getColour() == Colour.BLACK) {
            if (!(newPosY == getPosY() - 1 || (newPosY == getPosY() - 2 && !isMoved && board.getPieceAt(newPosX, newPosY) == null))) {
                return false;
            }
        }

        // TODO: En passant
        if (board.getPieceAt(newPosX, newPosY) == null && xDiff == 1 ) {
            return false;
        }

        System.out.println("Pawn position is x: " + getPosX() + ", y: " + getPosY());

        return board.getPieceAt(newPosX, newPosY) == null || xDiff != 0;
    }

    @Override
    public boolean move(int newPosX, int newPosY, ChessBoard board) {
        if (validateMove(newPosX, newPosY, board) && super.move(newPosX, newPosY, board)) {
            setMoved(true);
            return true;
        }
        return false;
    }
}
