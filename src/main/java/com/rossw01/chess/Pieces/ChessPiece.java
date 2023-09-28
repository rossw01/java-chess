package com.rossw01.chess.Pieces;

import com.rossw01.chess.Board.ChessBoard;

import java.util.List;

public abstract class ChessPiece extends Piece {
    private ChessPieces pieceType;
    public ChessPiece(ChessPieces pieceType) {
        this.pieceType = pieceType;
    }
    public ChessPieces getPieceType() {
        return this.pieceType;
    }
    public void setPieceType(ChessPieces newPieceType) {
        this.pieceType = newPieceType;
    }
    public abstract boolean validateMove(int newPosX, int newPosY, ChessBoard board);

    /**
     * Used to check whether a proposed move will result in mate in 1
     * @param posX new x location for piece
     * @param posY new y location for piece
     * @param piece piece to be moved
     * @param board checks pieces locations
     * @return returns true if the proposed move will result in mate
     */
    public boolean moveCausesMate(int posX, int posY, ChessPiece piece, ChessBoard board) {
        ChessBoard boardCopy = new ChessBoard(board);

        final int tempX = piece.getPosX();
        final int tempY = piece.getPosY();

        boardCopy.removePieceAt(tempX, tempY, piece);
        boardCopy.setPieceAt(posX, posY, piece);

        List<ChessPiece> enemyPieces = piece.getColour() == Colour.BLACK ? boardCopy.getWhitePieces() : boardCopy.getBlackPieces();
        List<ChessPiece> friendlyPieces = piece.getColour() == Colour.BLACK ? boardCopy.getBlackPieces() : boardCopy.getWhitePieces();
        ChessPiece myKing = friendlyPieces.stream().filter(e -> e.pieceType == ChessPieces.KING).findFirst().orElse(null);

        assert myKing != null;

        for (ChessPiece curr : enemyPieces) {
            if (piece.getPieceType() == ChessPieces.KING) {
                if ((curr.validateMove(posX, posY, boardCopy) &&
                        !curr.isPathBlocked(posX, posY, boardCopy))) {
                    return true;
                }
            } else {
                if ((curr.validateMove(myKing.getPosX(), myKing.getPosY(), boardCopy) &&
                        !curr.isPathBlocked(myKing.getPosX(), myKing.getPosY(), boardCopy))) {
                    return true;
                }
            }
        }

        return false;
    }
    public boolean isPathBlocked(int newPosX, int newPosY, ChessBoard board) {
        int xIncrement = getPosX() < newPosX ? 1 : -1;
        int yIncrement = getPosY() < newPosY ? 1 : -1;
        int xDiff = Math.abs(newPosX - getPosX());
        int yDiff = Math.abs(newPosY - getPosY());
        if (getPieceType() == ChessPieces.KNIGHT) return false;

        if (xDiff == yDiff) {
            for (int i = 1; i < xDiff; i++) {
                if (board.getPieceAt(getPosX() + (xIncrement * i), getPosY() + (yIncrement * i)) != null ) {
                    return true;
                }
            }
            return false;
        }
        if (xDiff > 0 && yDiff == 0) {
            for (int i = 1; i < xDiff; i++) {
                if (board.getPieceAt(getPosX() + (xIncrement * i), getPosY()) != null) return true;
            }
            return false;
        }
        if (xDiff == 0 && yDiff > 0) {
            for (int i = 1; i < yDiff; i++) {
                if (board.getPieceAt(getPosX(), getPosY() + (yIncrement * i)) != null) return true;
            }
            return false;
        }
        return true;
    }
    public boolean isMovingInPlace(int posX, int posY, int newPosX, int newPosY) {
        return (posX == newPosX && posY == newPosY);
    }
    public boolean isOutOfBounds(int newPosX, int newPosY) {
        return (!(newPosX >= 1 && newPosX <= 8 && newPosY >= 1 && newPosY <= 8));
    }
    public boolean isValidMove(ChessPiece piece, int x, int y, ChessBoard board) {
        return (piece.validateMove(x + 1, board.Y_WIDTH - y, board) &&
                (board.getPieceAt(x + 1, board.Y_WIDTH - y) == null ||
                        board.getPieceAt(x + 1, board.Y_WIDTH - y).getColour() == (piece.getColour() == Colour.WHITE ? Colour.BLACK : Colour.WHITE))  &&
                !piece.isPathBlocked(x + 1, board.Y_WIDTH - y, board) &&
                !piece.moveCausesMate(x + 1, board.Y_WIDTH - y, piece, board));
    }
    public boolean move(int newPosX, int newPosY, ChessBoard board) {
        if (isValidMove(this, newPosX - 1, board.Y_WIDTH - newPosY, board)) {
            setPosX(newPosX);
            setPosY(newPosY);
            return true;
        }
        return false;
    }
}
