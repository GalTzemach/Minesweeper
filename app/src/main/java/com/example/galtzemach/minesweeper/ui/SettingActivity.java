package com.example.galtzemach.minesweeper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.galtzemach.minesweeper.R;

public class SettingActivity extends AppCompatActivity {

    private boolean isSounde;
    private boolean isVebration;
    private boolean isFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_setting);
        relativeLayout.setGravity(Gravity.CENTER);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView txtOptional = new TextView(this);
        txtOptional.setText("Optional (not work)");
        txtOptional.setTextSize(30);

        CheckBox soundCheckBox = new CheckBox(this);
        soundCheckBox.setText("Sound");

        CheckBox vibrationCheckBox = new CheckBox(this);
        vibrationCheckBox.setText("Vibration");

        CheckBox fullScreenCheckBox = new CheckBox(this);
        fullScreenCheckBox.setText("Full Screen");

        linearLayout.addView(txtOptional);
        linearLayout.addView(soundCheckBox);
        linearLayout.addView(vibrationCheckBox);
        linearLayout.addView(fullScreenCheckBox);
        relativeLayout.addView(linearLayout);
    }

    public SettingActivity() {
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
