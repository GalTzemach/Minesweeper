package com.example.galtzemach.minesweeper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.galtzemach.minesweeper.R;

public class HelpActivity extends AppCompatActivity {

    //private AlphabeticIndex.Bucket.LabelType label;///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_help);
        ScrollView scrollView = new ScrollView(this);
        TextView textView = new TextView(this);
        textView.setText("Instructions for MineSweeper\n" +
                "\n" +
                "Quick Start:\n" +
                "\n" +
                "You are presented with a gameBoard of squares. Some squares contain mines (bombs), others don't. If you click on a square containing a bomb, you lose. If you manage to click all the squares (without clicking on any bombs) you win.\n" +
                "Clicking a square which doesn't have a bomb reveals the number of neighbouring squares containing bombs. Use this information plus some guess work to avoid the bombs.\n" +
                "To open a square, point at the square and click on it. To mark a square you think is a bomb, point and right-click (or hover with the mouse and press Space).\n" +
                "Detailed Instructions:\n" +
                "\n" +
                "A squares \"neighbours\" are the squares adjacent above, below, left, right, and all 4 diagonals. Squares on the sides of the gameBoard or in a corner have fewer neighbors. The gameBoard does not wrap around the edges.\n" +
                "If you open a square with 0 neighboring bombs, all its neighbors will automatically open. This can cause a large area to automatically open.\n" +
                "To remove a bomb marker from a square, point at it and right-click again.\n" +
                "The first square you open is never a bomb.\n" +
                "If you mark a bomb incorrectly, you will have to correct the mistake before you can win. Incorrect bomb marking doesn't kill you, but it can lead to mistakes which do.\n" +
                "You don't have to mark all the bombs to win; you just need to open all non-bomb squares.\n" +
                "Right-clicking twice will give you a question mark symbol which can be useful if you are unsure about a square\n" +
                "Click the yellow happy face to start a new game.\n" +
                "Status Information:\n" +
                "\n" +
                "The upper left corner contains the number of bombs left to find. The number will update as you mark and unmark squares.\n" +
                "The upper right corner contains a time counter. The timer will max out at 999 (16 minutes 39 seconds).\n" +
                "Click on the time to switch to the number of moves counter. Click again to switch back to the time.\n" +
                "Options and Enhancements:\n" +
                "\n" +
                "Learning Mode - Show the contents of all unopened cells. Scores do not count towards high scores, and the Opening Move option does not apply.\n" +
                "Opening Move - Not only will the first square never be a bomb, but neither will any of the neighbors.\n" +
                "Marks (?) - Right clicking on a marked bomb will change the flag into a question mark. Right clicking again will change it back into an unmarked square.\n" +
                "Area Open - If an open square has the correct number of marked neighboring bombs, click on the open square to open all remaining unopened neighbor squares all at once. If an incorrect number of neighbors are marked, or all neighbors are marked or open, clicking the square has no effect. If an incorrect neighbor is marked, this will cause instant death.\n" +
                "Open Remaining - Once the correct number of bombs have been marked, the bomb counter will turn blue. Click on the blue bomb counter to open all remaining cells. If any bombs are incorrectly marked, this will cause instant death.\n" +
                "Close");
        textView.setTextSize(19);
        scrollView.addView(textView);
        relativeLayout.addView(scrollView);
    }

    public HelpActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
