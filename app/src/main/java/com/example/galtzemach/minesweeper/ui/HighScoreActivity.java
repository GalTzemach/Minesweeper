package com.example.galtzemach.minesweeper.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.galtzemach.minesweeper.R;

public class HighScoreActivity extends AppCompatActivity {

    TextView playerName;
    TextView highScore;
    String[] names;
    Integer[] scores;

    private static final String TAG = "HighScoreActivity";
    // final int WIDTH_BUTTON = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_high_score);
        relativeLayout.setPadding(30,0,0,0);

        LinearLayout mainLinearVertical = new LinearLayout(this);
        mainLinearVertical.setOrientation(LinearLayout.VERTICAL);
        mainLinearVertical.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mainLinearVertical.setGravity(Gravity.TOP);
        mainLinearVertical.setPadding(0,0,0,0);

        LinearLayout buttonLinearHorizontal = new LinearLayout(this);
        buttonLinearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        buttonLinearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonLinearHorizontal.setGravity(Gravity.TOP);
        buttonLinearHorizontal.setPadding(0,30,0,0); // padding from up

        Display display = getWindowManager().getDefaultDisplay();

        Button btEasy = new Button(this);
        btEasy.setWidth((display.getWidth()-50)/3);
        btEasy.setText("Easy");

        Button btMedium = new Button(this);
        btMedium.setWidth((display.getWidth()-50)/3);
        //btMedium.setHigh /// i want another high for button
        btMedium.setText("Medium");

        Button btHard = new Button(this);
        btHard.setWidth((display.getWidth()-50)/3);
        btHard.setText("Hard");

        final TextView titleTextView = new TextView(this);
        titleTextView.setText("best result for Easy:");
        titleTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titleTextView.setTextSize(30);

        TextView playerNameTitle = new TextView(this);
        playerNameTitle.setText("Player name:" + "\n");
        playerNameTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        playerNameTitle.setTextSize(30);

        playerName = new TextView(this);
        playerName.setText("Not exist");
        playerName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        playerName.setTextSize(30);

        TextView highScoreTitle = new TextView(this);
        highScoreTitle.setText("Time :" + "\n");
        highScoreTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        highScoreTitle.setTextSize(30);

        highScore = new TextView(this);
        highScore.setText("000");
        highScore.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        highScore.setTextSize(30);


        buttonLinearHorizontal.addView(btEasy);
        buttonLinearHorizontal.addView(btMedium);
        buttonLinearHorizontal.addView(btHard);

        mainLinearVertical.addView(buttonLinearHorizontal);
        mainLinearVertical.addView(titleTextView);
        mainLinearVertical.addView(playerNameTitle);
        mainLinearVertical.addView(playerName);
        mainLinearVertical.addView(highScoreTitle);
        mainLinearVertical.addView(highScore);

        relativeLayout.addView(mainLinearVertical);

        loadPlayersScores();

        btEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTextView.setText("best result for Easy:");
                showResults(0);
            }
        });

        btMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTextView.setText("best result for Medium:");
                showResults(1);
            }
        });

        btHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTextView.setText("best result for Hard:");
                showResults(2);
            }
        });

        btEasy.callOnClick();

    }

    private void showResults(int i) {
        if (names[i] != null && scores[i] != -1) {
            playerName.setText(names[i]);
            highScore.setText(String.valueOf(scores[i]));
        } else {
            playerName.setText("Not exist");
            highScore.setText("000");
        }
    }

    public void loadPlayersScores() {

        names = new String[3];
        scores = new Integer[3];

        SharedPreferences scoresPreferences = getSharedPreferences("scoresss", MODE_PRIVATE);

        for (int i = 0; i < 3; i ++) {
            String playerName = scoresPreferences.getString("player" + i + "name", null);
            Integer playerScore = scoresPreferences.getInt("player" + i + "score", -1);

            if (playerName != null && playerScore != -1) {
                names[i] = playerName;
                scores[i] = playerScore;
            }
        }

        Log.d(TAG, "loadPlayersScores: loaded the following arrays: " + names+ "\n" + scores); ///
    }

}
