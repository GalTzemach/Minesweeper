package com.example.galtzemach.minesweeper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.galtzemach.minesweeper.R;

import static android.view.Gravity.CENTER;

public class LevelActivity extends AppCompatActivity {

    final int WIDTH_BUTTON = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        LinearLayout levelLayout = new LinearLayout(this);
        levelLayout.setGravity(CENTER);///
        levelLayout.setOrientation(LinearLayout.VERTICAL);
        levelLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //create easy button
        Button easyButton = new Button(this);
        easyButton.setText("Easy");
        easyButton.setWidth(WIDTH_BUTTON);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent easy = new Intent(getApplicationContext(), GameActivity.class);
                easy.putExtra("level", new int[]{10, 10, 5, 0});//[row,col,#mines,levelNumber]
                startActivity(easy);
            }
        });

        //create medium button
        Button mediumButton = new Button(this);
        mediumButton.setText("Medium");
        mediumButton.setWidth(WIDTH_BUTTON);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medium = new Intent(getApplicationContext(), GameActivity.class);
                medium.putExtra("level", new int[]{10, 10, 10, 1});//[row,col,#mines,levelNumber]
                startActivity(medium);
            }
        });

        //create Hard button
        Button hardButton = new Button(this);
        hardButton.setText("Hard");
        hardButton.setWidth(WIDTH_BUTTON);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hard = new Intent(getApplicationContext(), GameActivity.class);
                hard.putExtra("level", new int[]{5, 5, 10 , 2});//[row,col,#mines,levelNumber]
                startActivity(hard);
            }
        });

        //add to levelLayout
        levelLayout.addView(easyButton);
        levelLayout.addView(mediumButton);
        levelLayout.addView(hardButton);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_level);
        relativeLayout.addView(levelLayout);
        relativeLayout.setGravity(Gravity.CENTER);
    }

}
