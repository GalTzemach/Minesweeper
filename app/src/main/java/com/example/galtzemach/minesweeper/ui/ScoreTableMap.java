package com.example.galtzemach.minesweeper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.galtzemach.minesweeper.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class ScoreTableMap extends AppCompatActivity implements OnMapReadyCallback{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table_map);

        int scrWidth  = getWindowManager().getDefaultDisplay().getWidth();
        int scrHeight = getWindowManager().getDefaultDisplay().getHeight();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_score_table_map);
        ///relativeLayout.setBackgroundColor(Color.CYAN);
        relativeLayout.setPadding(0,0,0,0);

        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams wLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams wmLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams mwLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(mwLayoutParams);
        ///linearLayout.setBackgroundColor(Color.GREEN);

        LinearLayout tableMapLinearLayout = new LinearLayout(this);
        tableMapLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        tableMapLinearLayout.setLayoutParams(mwLayoutParams);
        ///tableMapLinearLayout.setBackgroundColor(Color.BLUE);
        Button tableButton = new Button(this);
        tableButton.setText("Table");
        Button mapButton = new Button(this);
        mapButton.setText("Map");
        tableMapLinearLayout.addView(tableButton, scrWidth/2, LinearLayout.LayoutParams.WRAP_CONTENT);
        tableMapLinearLayout.addView(mapButton, scrWidth/2, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout levelLinearLayout = new LinearLayout(this);
        levelLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button easyButton = new Button(this);
        easyButton.setText("Easy");
        Button mediumButton = new Button(this);
        mediumButton.setText("Medium");
        Button hardButton = new Button(this);
        hardButton.setText("Hard");
        levelLinearLayout.addView(easyButton, scrWidth/3, LinearLayout.LayoutParams.WRAP_CONTENT);
        levelLinearLayout.addView(mediumButton, scrWidth/3, LinearLayout.LayoutParams.WRAP_CONTENT);
        levelLinearLayout.addView(hardButton, scrWidth/3, LinearLayout.LayoutParams.WRAP_CONTENT);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        linearLayout.addView(tableMapLinearLayout);
        linearLayout.addView(levelLinearLayout);
        relativeLayout.addView(linearLayout);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
