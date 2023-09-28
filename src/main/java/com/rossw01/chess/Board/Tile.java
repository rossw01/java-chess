package com.rossw01.chess.Board;

import com.rossw01.chess.Pieces.ChessPiece;
import com.rossw01.chess.Pieces.Colour;

public class Tile {
    private final Colour colour;
    private ChessPiece piece;
    public Tile(Colour colour) {
        this.colour = colour;
        this.piece = null;
    }
    public Tile(Colour colour, ChessPiece piece) {
        this.colour = colour;
        this.piece = piece;
    }
    public Colour getColour() {
        return colour;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
    public ChessPiece getPiece() {
        return this.piece;
    }
}
