package com.example.galtzemach.minesweeper.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.galtzemach.minesweeper.R;
import com.example.galtzemach.minesweeper.logic.GameBoard;
import com.example.galtzemach.minesweeper.logic.GameLogic;
import com.example.galtzemach.minesweeper.logic.Tile;

import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.Toast.LENGTH_LONG;

public class GameActivity extends AppCompatActivity {

    private GameLogic gameLogic;
    private ImageButton face;
    private Intent newGameIntent;
    private Handler timer = new Handler();
    private int secPassed = 0;
    private Button timerBt;
    private Button mineBt;
    private boolean isStart = false;
    private String name = "";
    private AlertDialog.Builder builder;
    private int levelNumber; // 0 = Easy, 1 = Medium, 2 = Hard
    private boolean isEnd = false;

    //private Image[] imageArr;


    //private static final String TAG = "GameActivity";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        newGameIntent = new Intent(getApplicationContext(), LevelActivity.class);

        //get level parameters & create game
        int[] arrLevel = getIntent().getIntArrayExtra("level");

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_game);
        relativeLayout.setGravity(Gravity.CENTER);

        LinearLayout verticalLinearLayout = new LinearLayout(this);
        verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLinearLayout.setGravity(View.SCROLL_INDICATOR_TOP);///?
        verticalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout upHorizontalLinearLayout = new LinearLayout(this);
        upHorizontalLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        upHorizontalLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR); //    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        upHorizontalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        upHorizontalLinearLayout.setPadding(0, 0, 0, 100);
        //upHorizontalLinearLayout.setBackgroundColor(Color.GREEN);///

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int high = display.getHeight();

        gameLogic = new GameLogic(new GameBoard(this, width, high,  arrLevel[0],arrLevel[1],arrLevel[2]));
        levelNumber = arrLevel[3];

        face = new ImageButton(this);
        face.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        face.setPadding(0,0,0,0);
        face.setImageResource(R.drawable.thinking_face_200x200);
        face.setAdjustViewBounds(true);
        face.setScaleType(FIT_CENTER);

        // create # mines text
        mineBt = new Button(this);
        //mineBt.setText("#Mine");
        mineBt.setText(Integer.toString(gameLogic.getFlagsLeft()));

        // create timer button
        timerBt = new Button(this);
        timerBt.setText("Timer");

        upHorizontalLinearLayout.addView(mineBt);
        upHorizontalLinearLayout.addView(face);
        upHorizontalLinearLayout.addView(timerBt);

        verticalLinearLayout.addView(upHorizontalLinearLayout);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //scrollView.setBackgroundColor(Color.BLUE);///
        scrollView.addView(createBoard());
        verticalLinearLayout.addView(scrollView);//createBoard return LinearLayout

        relativeLayout.addView(verticalLinearLayout);

        //startTimer();///start in first press
    }

    public void startTimer() {
        if (secPassed == 0) {
            timer.removeCallbacks(updateTimeElapsed);

            // tell timer to run call back after 1 second
            timer.postDelayed(updateTimeElapsed, 1000);
        }
    }

    private Runnable updateTimeElapsed = new Runnable() {
        public void run() {
            long currentMilliseconds = System.currentTimeMillis();
            ++secPassed;
            timerBt.setText(Integer.toString(secPassed) + " Sec");

            // add notification
            timer.postAtTime(this, currentMilliseconds);

            // notify to call back after 1 seconds
            // basically to remain in the timer loop
            if (isEnd == false)
                timer.postDelayed(updateTimeElapsed, 1000);
        }
    };

    public LinearLayout createBoard(){

        //ScrollView scrollView = new ScrollView(this);
        //scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //create matrix by linear layout
        for (int i = 0; i < gameLogic.gameBoard.getRow(); i++){
            LinearLayout colLinearLayout = new LinearLayout(this);
            colLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(colLinearLayout);
            for (int j = 0; j < gameLogic.gameBoard.getCol(); j ++){
                colLinearLayout.addView(gameLogic.gameBoard.getTheBoard()[i][j]);//add tiles
                final Tile tile = gameLogic.gameBoard.getTheBoard()[i][j];
                tile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Start game
                        if (isStart == false){
                            isStart = true;
                            startTimer();
                        }

                        if (tile.isTaken() == false) {
                            tile.setTaken(true);
                            tile.showImage();
                            if (tile.getValue() == -1) { //loss
                                gameOver();
                                isEnd = true;
                            } else if (tile.getValue() == 0)
                                gameLogic.gameBoard.openAllBlank(tile);

                            if (gameLogic.gameBoard.isWin() && !isEnd) {
                                won();
                                isEnd = true;
                            }


                        }else
                            Toast.makeText(getApplicationContext(),"Alredy tekan",Toast.LENGTH_LONG).show();
                    }
                });

                tile.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) { ////
                        if (isStart == false){
                            isStart = true;
                            startTimer();
                        }
                        if (tile.isTaken() == false && tile.isFlagged() == false) {
                            if (gameLogic.canFlag(tile) == true) {
                                tile.setFlagged(true);
                                //tile.setTaken(true);
                                tile.showImage();
                                gameLogic.setFlagsLeft(gameLogic.getFlagsLeft() - 1);
                                mineBt.setText(Integer.toString(gameLogic.getFlagsLeft()));
                                if (gameLogic.gameBoard.isWin() == true)
                                    won();
                            } else {
                                Toast.makeText(getApplicationContext(), "No more flags", LENGTH_LONG).show();
                            }

//                        if (tile.longClicked() == true) ;
//                        gameLogic.setFlagsLeft(gameLogic.getFlagsLeft() - 1);

                        } else {
                            if (tile.isFlagged() == true) {
                                tile.setFlagged(false);
                                //
                                tile.setTaken(false);//?
                                //tile.showImage();
                                tile.setImageResource(R.drawable.close);
                                tile.setAdjustViewBounds(true);
                                tile.setScaleType(FIT_CENTER);
                                tile.setPadding(5,5,5,5);
                                gameLogic.setFlagsLeft(gameLogic.getFlagsLeft() + 1);
                                mineBt.setText(Integer.toString(gameLogic.getFlagsLeft()));
                            }else ///remove condition?
                                Toast.makeText(getApplicationContext(), "Already taken", LENGTH_LONG).show();
                        }
                        return true;
                    }
                });

            }
        }
        //scrollView.addView(linearLayout);
        return linearLayout;
    }

    private void gameOver() {
        if (gameLogic.isWon() == false)
            Toast.makeText(this, "gameOver", LENGTH_LONG).show();
        face.setImageResource(R.drawable.cry_face_200x200);
        face.setAdjustViewBounds(true);
        face.setScaleType(FIT_CENTER);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        openAllTile();
        gameLogic.setLoss(true);
    }

    private void openAllTile() {
        for (int i = 0; i < gameLogic.gameBoard.getRow(); i++) {
            for (int j = 0; j < gameLogic.gameBoard.getCol(); j++)
                gameLogic.gameBoard.getTheBoard()[i][j].showImage();
        }
    }

    private void won() {
        Toast.makeText(this, "You WON!", LENGTH_LONG).show();
        face.setImageResource(R.drawable.imgres);
        face.setAdjustViewBounds(true);
        face.setScaleType(FIT_CENTER);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        openAllTile();
        gameLogic.setWon(true);
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        builder = new AlertDialog.Builder(this);
        builder.setTitle("You Won!");
        builder.setMessage("Enter your name");
        //View wonView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_game, )
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input.getText().toString();

                /// if need to save
                savePlayersScores(levelNumber);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //builder.show();
    }

    private void savePlayersScores(int i) {

        String nameToSave = name;
        int timeToSave = secPassed;

        SharedPreferences scoresPreferences = getSharedPreferences("scoresss", MODE_PRIVATE);
        SharedPreferences.Editor scoresEditor = scoresPreferences.edit();

            scoresEditor.putString("player" + i + "name", nameToSave);
            scoresEditor.putInt("player" + i + "score", timeToSave);

        scoresEditor.apply();
    }

}
