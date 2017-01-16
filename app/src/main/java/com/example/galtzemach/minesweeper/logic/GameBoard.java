package com.example.galtzemach.minesweeper.logic;

import android.content.Context;
import android.util.Log;

import com.example.galtzemach.minesweeper.R;

import java.util.Random;

/**
 * Created by Gal Tzemach on 28/11/2016.
 */

public class GameBoard {

    private int row;
    private int col;
    private int width;
    private int high;
    private int numberOfMines;
    private Tile[][] theBoard;
    private Context context;

    private static final String TAG = "GameBoard";

    public GameBoard(Context context, int width, int High, int row, int col, int numberOfMines) {
        this.context = context;
        this.row = row;
        this.col = col;
        this.width = (width - 110) / col;
        this.high = this.width; // same size
        this.numberOfMines = numberOfMines;
        this.theBoard = new Tile[row][col];
        createBoard();
    }

    public void addMineCol(int colNumber) {
        //put mines on specific col
        for (int rowMine = 0; rowMine < getRow(); rowMine++){
            int colMine = colNumber;
            if (theBoard[rowMine][colMine].getValue() != -1) { //check if not exist mine
                int lottery = new Random().nextInt(2); // rand if add or not
                if (lottery == 0) { // add mine to this tile
                    theBoard[rowMine][colMine].setValue(-1);
                    numberOfMines ++; /// update ui

                    // add value to the tiles around the mine
                    for (int j = -1; j <= 1 ; j++){
                        for (int k = -1; k <= 1 ; k++){
                            int indexRow = rowMine + j;
                            int indexCol = colMine + k;
                            if (indexRow >= 0 && indexRow < row && indexCol >= 0 && indexCol < col) {//check if valid index
                                if (theBoard[indexRow][indexCol].getValue() != -1) {//check if not exist mine
                                    theBoard[indexRow][indexCol].increaseValue();
                                    theBoard[indexRow][indexCol].showImage();
                                    Log.d(TAG, "theBoard[" + rowMine + "][" + colMine + "] = showImage");
                                    ///Log.d(TAG, "Tile[" + indexRow + "][" + indexCol + "] = " + theBoard[indexRow][indexCol].getValue());
                                    ///Log.d(TAG, "rowMine = [" + rowMine + "] colMine = [" + colMine + "] counter= [" + x++ + "]");
                                }
                            }
                        }
                    }
                }

            }
            theBoard[rowMine][colMine].setTaken(false);
            theBoard[rowMine][colMine].showImage();
            theBoard[rowMine][colMine].setFlagged(false);
            //if (colMine != colNumber)
            //theBoard[rowMine][colMine].setImageResource(R.drawable.close);
            Log.d(TAG, "theBoard[" + rowMine + "][" + colMine + "] = photo close");
            Log.d(TAG, "theBoard[" + rowMine + "][" + colMine + "].value = " + theBoard[rowMine][colMine].getValue());
        }
        closeCol(colNumber);
    }

    private void closeCol(int indexCol) {
        for (int indexRow = 0; indexRow < getRow(); indexRow++) {
            theBoard[indexRow][indexCol].setImageResource(R.drawable.close);
        }
    }

    public void addMineRow(int rowNumber) {
        //put mines on specific row
        for (int colMine = 0; colMine < getCol(); colMine++){
            int rowMine = rowNumber;
            if (theBoard[rowMine][colMine].getValue() != -1) { //check if not exist mine
                int lottery = new Random().nextInt(2); // rand if add or not
                if (lottery == 0) { // add mine to this tile
                    theBoard[rowMine][colMine].setValue(-1);
                    numberOfMines++; /// update ui

                    // add value to the tiles around the mine
                    for (int j = -1; j <= 1; j++) {
                        for (int k = -1; k <= 1; k++) {
                            int indexRow = rowMine + j;
                            int indexCol = colMine + k;
                            if (indexRow >= 0 && indexRow < row && indexCol >= 0 && indexCol < col) {//check if valid index
                                if (theBoard[indexRow][indexCol].getValue() != -1) {//check if not exist mine
                                    theBoard[indexRow][indexCol].increaseValue();
                                    theBoard[indexRow][indexCol].showImage();
                                    ///Log.d(TAG, "Tile[" + indexRow + "][" + indexCol + "] = " + theBoard[indexRow][indexCol].getValue());
                                }
                            }
                        }
                    }
                }

            }
            theBoard[rowMine][colMine].setTaken(false);
            theBoard[rowMine][colMine].setFlagged(false);
            theBoard[rowMine][colMine].setImageResource(R.drawable.close);

        }
    }

    public void createBoard() {

        ///initial, check if needed?
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                theBoard[i][j] = new Tile(context, i, j, 0, false, false);

        //put mines
        for (int i = 0; i < numberOfMines; /*i++*/){
            int rowMine = new Random().nextInt(row);
            int colMine = new Random().nextInt(col);
            if (theBoard[rowMine][colMine].getValue() != -1) {//check if not exist mine
                theBoard[rowMine][colMine].setValue(-1);
                i++;

                // add value to the tiles around the mine
                for (int j = -1; j <= 1 ; j++){
                    for (int k = -1; k <= 1 ; k++){
                        int indexRow = rowMine + j;
                        int indexCol = colMine + k;
                        if (indexRow >= 0 && indexRow < row && indexCol >= 0 && indexCol < col)//check if valid index
                            if (theBoard[indexRow][indexCol].getValue() != -1)//check if not exist mine
                                theBoard[indexRow][indexCol].increaseValue();
                    }
                }
            }
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getNumberOfMines() {
        return this.numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public Tile[][] getTheBoard() {
        return this.theBoard;
    }

    public void openAllBlank(Tile tile) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                    int indexRow = tile.getPositionRow() + i;
                    int indexCol = tile.getPositionCol() + j;
                    if (indexRow >= 0 && indexRow < row && indexCol >= 0 && indexCol < col) {//index valid
                        Tile nextTile = theBoard[indexRow][indexCol];
                        if (nextTile.isTaken() == false) {//stop recursive
                            if (nextTile.getValue() != -1 && tile.getValue() == 0) {// new is number & tile is blank
                                nextTile.showImage();
                                nextTile.setTaken(true);
                                openAllBlank(nextTile);
                            }

                        }
                    }
            }
        }
    }

    public boolean isWin() {
        int countNotTaken = 0, counterMines = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Tile tile = theBoard[i][j];
                if (tile.isTaken() == false)
                    countNotTaken ++;
                if (tile.isFlagged() == true && tile.getValue() == -1)
                    counterMines ++;
            }
        }
        if (countNotTaken <= numberOfMines || counterMines == numberOfMines)
            return true;
        return false;
    }

}
