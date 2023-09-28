package com.rossw01.chess.Pieces;

import com.rossw01.chess.Pieces.Colour;

public class Piece {
    private int posX;
    private int posY;
    private Colour colour;

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public Colour getColour() {
        return colour;
    }
    public void setColour(Colour newColour) {
        this.colour = newColour;
    }
    protected void setPosX(int newPosX) {
        this.posX = newPosX;
    }
    protected void setPosY(int newPosY) {
        this.posY = newPosY;
    }
}
