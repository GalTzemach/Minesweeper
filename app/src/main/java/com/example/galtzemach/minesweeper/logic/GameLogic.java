package com.example.galtzemach.minesweeper.logic;

import android.content.Context;
import android.widget.ImageButton;

import java.sql.Time;

/**
 * Created by Gal Tzemach on 27/11/2016.
 */

public class GameLogic {

    //private int level;
    public GameBoard gameBoard;///private?
    private Time time;
    private int flagsLeft;
    private int tileTakenCounter;
    private ImageButton face;
    private int mode;
    private  boolean isWon = false;
    private boolean isLoss = false;


    public GameLogic(Context context, GameBoard gameBoard) {///remove context?
        this.gameBoard = gameBoard;
        this.flagsLeft = gameBoard.getNumberOfMines();
       // gameBoard.createBoard(); /// in GameBoard
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public boolean isLoss() {
        return isLoss;
    }

    public void setLoss(boolean loss) {
        isLoss = loss;
    }

    public void onClick(){

    }

    public void onPress(){

    }

    public void isMine(){

    }

    public ImageButton getFace() {
        return face;
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public void setFace(ImageButton face) {
        this.face = face;
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
