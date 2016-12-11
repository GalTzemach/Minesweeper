package com.example.galtzemach.minesweeper.logic;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.galtzemach.minesweeper.R;

import static android.widget.ImageView.ScaleType.FIT_CENTER;

/**
 * Created by Gal Tzemach on 28/11/2016.
 */

public class Tile extends ImageButton {

    private int positionRow;
    private int positionCol;
    private int value;
    private boolean isFlaged = false;
    private boolean isTaken = false;
    private boolean isX = false;
    private Context context;

    public Tile(Context context,int positionRow, int positionCol, int value, boolean isFlaged, boolean isTaken) {
        super(context);
        this.context = context;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.value = value;
        this.isFlaged = isFlaged;
        this.isTaken = isTaken;
        setLayoutParams(new LinearLayout.LayoutParams(99, 99));
        setImageResource(R.drawable.close);
        setAdjustViewBounds(true);
        setScaleType(FIT_CENTER);
        setPadding(5,5,5,5);
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public void setFlaged(boolean flaged) {
        isFlaged = flaged;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increaseValue() {
        this.value ++;
    }

    public void showImage() {

        if (isFlaged() == false) {
            switch (getValue()) { // check tile value
                case -1: // is mine
                    setImageResource(R.drawable.mine_bomb_200x200);
                    //gameOver();
                    break;
                case 0: // is empty
                    setImageResource(R.drawable.open);
                    //openAllBlank();
                    break;
                case 1: // all the rest near mine
                    setImageResource(R.drawable.number1_200x200);
                    break;
                case 2:
                    setImageResource(R.drawable.number2_200x200);
                    break;
                case 3:
                    setImageResource(R.drawable.number3_200x200);
                    break;
                case 4:
                    setImageResource(R.drawable.number4_200x200);
                    break;
                case 5:
                    setImageResource(R.drawable.number5_200x200);
                    break;
                case 6:
                    setImageResource(R.drawable.number6_200x200);
                    break;
                case 7:
                    setImageResource(R.drawable.number7_200x200);
                    break;
                case 8:
                    setImageResource(R.drawable.number8_200x200);
                    break;
            }
        }else if (isFlaged() == true)
            setImageResource(R.drawable.flag_200x200);

        setAdjustViewBounds(true);
        setScaleType(FIT_CENTER);
        setPadding(5,5,5,5);
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public boolean isFlaged() {
        return isFlaged;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public boolean isX() {
        return isX;
    }

    public boolean longClicked(){
        if (isTaken == false) {
            isFlaged = true;
            isTaken = true;
//            setImageResource(R.drawable.flag_200x200);
//            setAdjustViewBounds(true);
//            setScaleType(FIT_CENTER);
//            setPadding(5,5,5,5);
            showImage();
            return true;
        } else if (isFlaged == true)
            isFlaged = false;
        return false;
    }
}
