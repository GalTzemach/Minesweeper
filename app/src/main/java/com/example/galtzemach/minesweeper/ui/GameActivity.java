package com.example.galtzemach.minesweeper.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galtzemach.minesweeper.R;
import com.example.galtzemach.minesweeper.logic.GameBoard;
import com.example.galtzemach.minesweeper.logic.GameLogic;
import com.example.galtzemach.minesweeper.logic.LocalService;
import com.example.galtzemach.minesweeper.logic.Tile;

import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.Toast.LENGTH_LONG;

public class GameActivity extends AppCompatActivity implements LocalService.MyServiceListener {

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

    private static final String TAG = "GameActivity";
    private LocalService myService;
    boolean sensorFirstUsed = false;

    private TextView x_axis;
    private TextView y_axis;
    private TextView z_axis;

    float init_X;
    float init_y;
    float init_z;

    float currentX;
    float currentY;
    float currentZ;

    private boolean isRight;
    private boolean isLeft;
    private boolean isTop;
    private boolean isBottom;

    private int leftToRight = 0;
    private int rightToLeft;
    private int topToBottom = 0;
    private int bottomToTop;


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
            if (serviceBinder instanceof LocalService.ServiceBinder) {
                // We got THIS serviceBinder from the event 'onBind' in our service.
                setService(((LocalService.ServiceBinder) serviceBinder).getService());
            }
            Log.d(TAG, "onServiceConnected: " + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            setService(null);
            Log.d(TAG, "onServiceDisconnected: " + name);
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Bind to LocalService
        /// onCreate ???
        Intent intent = new Intent(this, LocalService.class);
        boolean bindingSucceeded = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "onStart: " + (bindingSucceeded ? "the binding succeeded..." : "the binding failed!"));


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

        x_axis = new TextView(this);
        y_axis = new TextView(this);
        z_axis = new TextView(this);

        x_axis.setText("x_axis = ---");
        y_axis.setText("y_axis = ---");
        z_axis.setText("z_axis = ---");

        verticalLinearLayout.addView(x_axis);
        verticalLinearLayout.addView(y_axis);
        verticalLinearLayout.addView(z_axis);

        relativeLayout.addView(verticalLinearLayout);

        //startTimer();///start in first press
    }

    public void setService(LocalService service) {
        if (service != null) {
            this.myService = service;
            service.setListener(this);
            service.startListening();
        } else {
            if (this.myService != null) {
                // remove the listener
                this.myService.setListener(null);
            }
            this.myService = null;
        }
    }

    public void startTimer() {
        if (secPassed == 0) {

            rightToLeft = gameLogic.gameBoard.getCol() -1;
            bottomToTop = gameLogic.gameBoard.getRow() - 1;
            sensorFirstUsed = false; ///

            timer.removeCallbacks(updateTimeElapsed);
            timer.removeCallbacks(checkAxis);

            // tell timer to run call back after 1 second
            timer.postDelayed(updateTimeElapsed, 1000);
            timer.postDelayed(checkAxis, 1000);
        }
    }

    private Runnable checkAxis = new Runnable() {

        public void run() {

            isRight = isLeft = isTop = isBottom = false;

            if (currentX > 5) {
                isLeft = true;
            }
            else if (currentX < -5) {
                isRight = true;
            }

            if (currentY > 5) {
                isTop = true;
            }
            else if (currentY < -5) {
                isBottom = true;
            }

            addMines(isLeft, isRight, isTop, isBottom);

            if (isEnd == false)
                timer.postDelayed(checkAxis, 1000);
        }
    };

    private void addMines(final boolean isLeft, final boolean isRight, final boolean isTop, final boolean isBottom) {
        /// run on ui thread ??
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (isLeft) { // add mine to col left
                    if (leftToRight < gameLogic.gameBoard.getCol() && leftToRight <= rightToLeft) {
                        gameLogic.gameBoard.addMineCol(leftToRight);
                        leftToRight ++;
                    }
                }

                if (isRight) { // add mine to col right
                    if (rightToLeft >= 0 && rightToLeft >= leftToRight) {
                        gameLogic.gameBoard.addMineCol(rightToLeft);
                        rightToLeft --;
                    }
                }

                if (isTop) { // add mine to row top
                    if (topToBottom < gameLogic.gameBoard.getRow() && topToBottom <= bottomToTop) {
                        gameLogic.gameBoard.addMineRow(topToBottom);
                        topToBottom ++;
                    }
                }

                if (isBottom) { // add mine to row bottom
                    if (bottomToTop >= 0 && bottomToTop >= topToBottom) {
                        gameLogic.gameBoard.addMineRow(bottomToTop);
                        bottomToTop --;
                    }
                }
            }
        });
        /// openAllTile();
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
                            //startService(view);
                            //startBoundService();
                        }
                        if (tile.isTaken() == false) {
                            tile.setTaken(true);
                            tile.showImage();
                            if (tile.getValue() == -1) { //loss
                                gameOver();
                                //stopService(view);
                                isEnd = true;
                                myService.stopListening();
                            } else if (tile.getValue() == 0)
                                gameLogic.gameBoard.openAllBlank(tile);

                            if (gameLogic.gameBoard.isWin() && !isEnd) {
                                won();
                                isEnd = true;
                                myService.stopListening();
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
                            //startService(view);
                            //startBoundService();
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
            ///stopService();
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

        // builder
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

    @Override
    protected void onPause() {
        super.onPause();

//        if (myService != null){
//            myService.stopListening();
//        }
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

    @Override
    public void onSensorEvent(final float[] values) {
        if (sensorFirstUsed == false) {
            init_X = values[0];
            init_y = values[1];
            init_z = values[2];
            sensorFirstUsed = true;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentX = values[0];
                currentY = values[1];
                currentZ = values[2];

                x_axis.setText("x_axis = " + currentX);
                y_axis.setText("y_axis = " + currentY);
                z_axis.setText("z_axis = " + currentZ);
            }
        });


    }

}
