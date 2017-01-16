package com.example.galtzemach.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.galtzemach.minesweeper.ui.HelpActivity;
import com.example.galtzemach.minesweeper.ui.LevelActivity;
import com.example.galtzemach.minesweeper.ui.HighScoreTableMapActivity;
import com.example.galtzemach.minesweeper.ui.SettingActivity;

public class MainActivity extends AppCompatActivity {

    final int WIDTH_BUTTON = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        LinearLayout mainMenuLinearLayout = new LinearLayout(this);
        mainMenuLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mainMenuLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainMenuLinearLayout.setGravity(Gravity.CENTER);

        //create Play button
        Button play = new Button(this);
        play.setWidth(WIDTH_BUTTON);
        play.setText("Play");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent level = new Intent(getApplicationContext(), LevelActivity.class);
                startActivity(level);
            }
        });

        //crete highScore button
        Button highScore = new Button(this);
        highScore.setWidth(WIDTH_BUTTON);
        highScore.setText("High score");
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent highScore = new Intent(getApplicationContext(), HighScoreTableMapActivity.class);
                startActivity(highScore);
            }
        });

        //crete setting button
        Button setting = new Button(this);
        setting.setWidth(WIDTH_BUTTON);
        setting.setText("Setting");
        setting.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(setting);
            }
        });

        //crete help button
        Button help = new Button(this);
        help.setWidth(WIDTH_BUTTON);
        help.setText("Help");
        help.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent help = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(help);
            }
        });

        //crete exit button
        Button exit = new Button(this);
        exit.setWidth(WIDTH_BUTTON);
        exit.setText("Exit");
        exit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        //crete start service button
//        final Button btStartService = new Button(this);
//        btStartService.setWidth(WIDTH_BUTTON);
//        btStartService.setText("Start Service");
//        btStartService.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        btStartService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startService(view);
//            }
//        });

//        //crete stop service button
//        final Button btStopService = new Button(this);
//        btStopService.setWidth(WIDTH_BUTTON);
//        btStopService.setText("Stop Service");
//        btStopService.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        btStopService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopService(view);
//            }
//        });


        //add
        mainMenuLinearLayout.addView(play);
        mainMenuLinearLayout.addView(highScore);
        mainMenuLinearLayout.addView(setting);
        mainMenuLinearLayout.addView(help);
        mainMenuLinearLayout.addView(exit);
        //mainMenuLinearLayout.addView(btStartService);
        //mainMenuLinearLayout.addView(btStopService);

        mainLayout.addView(mainMenuLinearLayout);
        mainLayout.setGravity(Gravity.CENTER);
    }

//    public void startService(View view) {
//        startService(new Intent(getBaseContext(), BoundService.class));
//    }
//
//    public void stopService(View view) {
//        stopService(new Intent(getBaseContext(), BoundService.class));
//    }

}
