package com.example.galtzemach.minesweeper.DB;//package com.example.galtzemach.minesweeper.DB;
//
///**
// * Created by Tal on 11/12/2016.
// */
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//
//import com.example.galtzemach.minesweeper.R;
//
////package com.example.afeka.myaasd;
//
//    public class Test extends AppCompatActivity implements ResultsActivityListener {
//
//        private static final String TAG = "MainActivity";
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//        }
//
//        @Override
//        protected void onResume() {
//            super.onResume();
//
//            loadPlayersScores();
//        }
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//        }
//
//        private void loadPlayersScores() {
//            String[] names = new  String[3];
//            Integer[] scores = new  Integer[3];
//
//            SharedPreferences scoresPreferences = getSharedPreferences("scores", MODE_PRIVATE);
//
//            for (int i =0; i < 3; i ++) {
//                String playerName = scoresPreferences.getString("player" + i + "name", null);
//                Integer playerScore = scoresPreferences.getInt("player" + i + "score", -1);
//
//                if (playerName != null && playerScore != -1) {
//                    scores[i] = playerScore;
//                    names[i] = playerName;
//                }
//            }
//
//            Log.d(TAG, "loadPlayersScores: loaded the following arrays: " + names+ "\n" + scores);
//        }
//
//        @Override
//        protected void onPause() {
//            super.onPause();
//
//            savePlayersScores();
//        }
//
//        private void savePlayersScores() {
//            String[] names = new  String[3];
//            names[0] = "Player1";
//            names[1] = "Player2";
//            names[2] = "Player3";
//
//            Integer[] scores = new  Integer[3];
//            scores[0] = 1;
//            scores[1] = 2;
//            scores[2] = 3;
//
//            SharedPreferences scoresPreferences = getSharedPreferences("scores", MODE_PRIVATE);
//            SharedPreferences.Editor scoresEditor = scoresPreferences.edit();
//
//            for (int i =0; i < 3; i ++) {
//                scoresEditor.putString("player" + i + "name", names[i]);
//                scoresEditor.putInt("player" + i + "score", scores[i]);
//            }
//
//            scoresEditor.apply();
//        }
//
//
//        public void onGameFinished(int score, String name) {
//            //
//        }
//
//}
