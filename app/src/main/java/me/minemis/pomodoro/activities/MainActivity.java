package me.minemis.pomodoro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.R;
import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.State;
import me.minemis.pomodoro.listeners.ButtonResetListener;
import me.minemis.pomodoro.listeners.ButtonStartPauseListener;
import me.minemis.pomodoro.listeners.NextButtonListener;
import me.minemis.pomodoro.listeners.TextTimerListener;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private ImageButton btnStartPause;
    private ImageButton btnReset;
    private ImageButton btnNext;
    private ProgressBar progressBar;
    private TextView text1, text2, text3;

    private CountdownManager countdownManager;

    @SuppressLint("StaticFieldLeak")
    private static MainActivity instance;
    private static RoundManager roundManager;

    public MainActivity() {
        super();
        instance = this;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roundManager = new RoundManager(this);

        textViewTimer = findViewById(R.id.txt_timer);
        btnStartPause = findViewById(R.id.btn_start);
        btnReset = findViewById(R.id.btn_reset);
        progressBar = findViewById(R.id.progress_bar);
        btnNext = findViewById(R.id.btn_next_phase);

        text1 = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);

        roundManager.nextRound(false);

        textViewTimer.setOnClickListener(new TextTimerListener(this));


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void makeNewCountdownManager(State state, boolean setStart) {

        if (!(countdownManager == null)){
            countdownManager.pauseTimer();
            countdownManager = null;
        }

        int time = roundManager.getTime(state);


        countdownManager = new CountdownManager(MainActivity.this, progressBar, textViewTimer, time * 60 * 1000); //1500000

        btnNext.setOnClickListener(new NextButtonListener(instance, roundManager, countdownManager));
        btnStartPause.setOnClickListener(new ButtonStartPauseListener(MainActivity.this, countdownManager));
        btnReset.setOnClickListener(new ButtonResetListener(MainActivity.this, countdownManager));

        if (setStart) {
            btnStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            countdownManager.startTimer();
            return;
        }
        btnStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
    }

    public TextView getTextViewTimer() {
        return textViewTimer;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ImageButton getBtnStartPause() {
        return btnStartPause;
    }

    public TextView getText1() {
        return text1;
    }

    public TextView getText2() {
        return text2;
    }

    public TextView getText3() {
        return text3;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public static RoundManager getRoundManager() {
        return roundManager;
    }
}