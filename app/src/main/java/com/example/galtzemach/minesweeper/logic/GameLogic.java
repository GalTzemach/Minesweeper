package com.example.galtzemach.minesweeper.logic;

import android.widget.ImageButton;

/**
 * Created by Gal Tzemach on 27/11/2016.
 */

public class GameLogic {

    public GameBoard gameBoard;
    private int flagsLeft;
    private ImageButton face;
    private  boolean isWon = false;
    private boolean isLoss = false;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.flagsLeft = gameBoard.getNumberOfMines();
    }

    public boolean isWon() {
        return this.isWon;
    }

    public void setWon(boolean won) {
        this.isWon = won;
    }

    public void setLoss(boolean loss) {
        this.isLoss = loss;
    }

    public int getFlagsLeft() {
        return this.flagsLeft;
    }

    public void setFlagsLeft(int flagsLeft) {
        this.flagsLeft = flagsLeft;
    }

    public boolean canFlag(Tile tile) {
        if (flagsLeft > 0 && tile.isTaken() == false)
            return  true;
        return  false;
    }

}
