package com.example.galtzemach.minesweeper.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.galtzemach.minesweeper.R;
import com.example.galtzemach.minesweeper.logic.RecordController;

public class ScoreTableMapActivity extends AppCompatActivity {
    public static final int MAP_FRAGMENT = 1;
    public static final int TABLE_FRAGMENT = 0;

    private RecordController recordController;
    private TableFragment tableViewFragment;
    private MapFragment mapFragment;
    private FragmentTransaction ft;
    private LinearLayout myMainLayout;
    private int currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table_map);

        myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        myMainLayout.setId(R.id.recordActivityLL);

        addContentView(myMainLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        recordController = new RecordController(this);

        int scrWidth  = getWindowManager().getDefaultDisplay().getWidth();

        LinearLayout.LayoutParams mwLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(mwLayoutParams);

        LinearLayout tableMapLinearLayout = new LinearLayout(this);
        tableMapLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        tableMapLinearLayout.setLayoutParams(mwLayoutParams);

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

        linearLayout.addView(tableMapLinearLayout);
        linearLayout.addView(levelLinearLayout);

        myMainLayout.addView(linearLayout);


        tableViewFragment = TableFragment.newInstance(RecordController.BEGINNER_LEVEL);
        tableViewFragment.setRecordController(recordController);

        mapFragment = MapFragment.newInstance(RecordController.BEGINNER_LEVEL);
        mapFragment.setRecordController(recordController);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(myMainLayout.getId(), tableViewFragment).commit();
        currentFragment = TABLE_FRAGMENT;

        tableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(myMainLayout.getId(), tableViewFragment).commit();
                currentFragment = TABLE_FRAGMENT;
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(myMainLayout.getId(), mapFragment).commit();
                currentFragment = MAP_FRAGMENT;
            }
        });

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment == TABLE_FRAGMENT)
                    tableViewFragment.updateTable(RecordController.BEGINNER_LEVEL);
                else
                    mapFragment.updateMap(RecordController.BEGINNER_LEVEL);
            }
        });
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment == TABLE_FRAGMENT)
                    tableViewFragment.updateTable(RecordController.ADVANCED_LEVEL);
                else
                    mapFragment.updateMap(RecordController.ADVANCED_LEVEL);
            }
        });
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment == TABLE_FRAGMENT)
                    tableViewFragment.updateTable(RecordController.EXPERT_LEVEL);
                else
                    mapFragment.updateMap(RecordController.EXPERT_LEVEL);
            }
        });
    }
}
