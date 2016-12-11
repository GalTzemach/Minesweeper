package com.example.galtzemach.minesweeper.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tal on 11/12/2016.
 */

public class SaveLoadData {

    private static final String TAG = "SaveLoadData";

    String[] names;
    Integer[] scores;
    Context activity;
    SharedPreferences scoresPreferences;

    public SaveLoadData(Context activity) {
        this.activity = activity;
        this.scoresPreferences= activity.getSharedPreferences("scores", MODE_PRIVATE);
        names = new  String[3];
        names[0] = "Player1";
        names[1] = "Player2";
        names[2] = "Player3";

        scores = new  Integer[3];
        scores[0] = 1;
        scores[1] = 2;
        scores[2] = 3;

    }


    private void loadPlayersScores() {String[] names = new  String[3];
        Integer[] scores = new  Integer[3];

        SharedPreferences scoresPreferences = activity.getSharedPreferences("scores", MODE_PRIVATE);

        for (int i =0; i < 3; i ++) {
            String playerName = scoresPreferences.getString("player" + i + "name", null);
            Integer playerScore = scoresPreferences.getInt("player" + i + "score", -1);

            if (playerName != null && playerScore != -1) {
                scores[i] = playerScore;
                names[i] = playerName;
            }
        }

        Log.d(TAG, "loadPlayersScores: loaded the following arrays: " + names+ "\n" + scores);
    }

    private void savePlayersScores() {
        String[] names = new  String[3];
        names[0] = "Player1";
        names[1] = "Player2";
        names[2] = "Player3";

        Integer[] scores = new  Integer[3];
        scores[0] = 1;
        scores[1] = 2;
        scores[2] = 3;

        SharedPreferences scoresPreferences = activity.getSharedPreferences("scores", MODE_PRIVATE);
        SharedPreferences.Editor scoresEditor = scoresPreferences.edit();

        for (int i =0; i < 3; i ++) {
            scoresEditor.putString("player" + i + "name", names[i]);
            scoresEditor.putInt("player" + i + "score", scores[i]);
        }

        scoresEditor.apply();
    }









}
