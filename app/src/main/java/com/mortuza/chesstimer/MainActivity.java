package com.mortuza.chesstimer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // declare variables
    Button btnPlayerTimer;
    Button btnOpponentTimer;
    Button btn_refresh;
    int timeOne = 300;
    int timeTwo = 300;
    int seconds = timeOne;
    int opponentSeconds = timeTwo;
    boolean flagTimer;
    boolean flagTimerOpponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // For Screen always on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // initialize xml component
        btnPlayerTimer = (Button) findViewById(R.id.btn_player_timer);
        btnOpponentTimer = (Button) findViewById(R.id.btn_opponent_timer);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);

        // set click listener
        btnPlayerTimer.setOnClickListener(this);
        btnOpponentTimer.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);

        // set Fonts
       /* Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/custom_font.ttf");
        btnPlayerTimer.setTypeface(typeface);
        btnOpponentTimer.setTypeface(typeface);*/

        // call those method
        timerPlayer();
        timerOpponent();
        setTextForTheFirstTime();
    }

    // buttons click action
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_player_timer:
                btnPlayerTimer.setAlpha(0.3f);
                btnOpponentTimer.setAlpha(1.0f);
                flagTimer = false;
                flagTimerOpponent = true;
                break;
            case R.id.btn_opponent_timer:
                btnOpponentTimer.setAlpha(0.3f);
                btnPlayerTimer.setAlpha(1.0f);
                flagTimerOpponent = false;
                flagTimer = true;
                break;
            case R.id.btn_refresh:
                reset();
                break;


        }
    }

    // method for player
    public void timerPlayer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600)/60;
                int secs = seconds % 60;

                String time = String.format("%02d:%02d",minutes,secs);

                if (seconds<=0){
                    gameOver(btnPlayerTimer);
                }

                if (flagTimer && seconds > 0 && opponentSeconds > 0){
                    seconds--;
                    btnPlayerTimer.setText(time);

                }
                handler.postDelayed(this,1000);

            }
        });
    }

    // method for opponent player
    public void timerOpponent(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = opponentSeconds / 3600;
                int minutes = (opponentSeconds % 3600)/60;
                int secs = opponentSeconds % 60;

                String time = String.format("%02d:%02d",minutes,secs);
                if (opponentSeconds<=0){
                    gameOver(btnOpponentTimer);
                }
                if (flagTimerOpponent && seconds > 0 && opponentSeconds > 0){
                    opponentSeconds--;
                    btnOpponentTimer.setText(time);
                }
                handler.postDelayed(this,1000);

            }
        });
    }

    public void reset(){
        btnPlayerTimer.setAlpha(1.0f);
        btnOpponentTimer.setAlpha(1.0f);
        seconds = timeOne;
        opponentSeconds = timeTwo;
        flagTimer = false;
        flagTimerOpponent = false;
        int minutes = (seconds % 3600)/60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d",minutes,secs);
        btnPlayerTimer.setText(time);
        btnOpponentTimer.setText(time);
    }

    public void gameOver(Button button){
        flagTimer = false;
        flagTimerOpponent = false;
        button.setText("You Lose!");
    }

    public void setTextForTheFirstTime(){
        int minutes = (seconds % 3600)/60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d",minutes,secs);
        btnPlayerTimer.setText(time);
        btnOpponentTimer.setText(time);
    }


}
