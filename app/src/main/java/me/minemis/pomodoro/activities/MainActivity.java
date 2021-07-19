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
import me.minemis.pomodoro.listeners.main.ButtonResetListener;
import me.minemis.pomodoro.listeners.main.ButtonStartPauseListener;
import me.minemis.pomodoro.listeners.main.NextButtonListener;
import me.minemis.pomodoro.listeners.main.TextTimerListener;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private ImageButton btnStartPause;
    private ImageButton btnReset;
    private ImageButton btnNext;
    private ProgressBar progressBar;
    private TextView text1, text2, text3;
    private TextView txtCurrentState;

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
        assignValues();

        roundManager.nextRound(false);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void makeNewCountdownManager(State state, boolean setStart) {

        if (!(countdownManager == null)){
            countdownManager.pauseTimer();
            countdownManager = null;
        }

        int time =          roundManager.getValue(state);

        countdownManager =  new CountdownManager(this, time * 60 * 1000); //1500000

        btnNext             .setOnClickListener(new NextButtonListener(instance));
        btnStartPause       .setOnClickListener(new ButtonStartPauseListener(MainActivity.this));
        btnReset            .setOnClickListener(new ButtonResetListener(MainActivity.this, countdownManager));

        if (setStart) {
            btnStartPause   .setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            countdownManager.startTimer();
            return;
        }
        btnStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
    }

    private void assignValues() {
        roundManager =      new RoundManager(this);

        textViewTimer =     findViewById(R.id.txt_timer);
        btnStartPause =     findViewById(R.id.btn_start);
        btnReset =          findViewById(R.id.btn_reset);
        progressBar =       findViewById(R.id.progress_bar);
        btnNext =           findViewById(R.id.btn_next_phase);
        txtCurrentState =   findViewById(R.id.txt_current_state);

        text1 =             findViewById(R.id.textView);
        text2 =             findViewById(R.id.textView2);
        text3 =             findViewById(R.id.textView3);

        textViewTimer       .setOnClickListener(new TextTimerListener(this));
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
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

    public TextView getTxtCurrentState() {
        return txtCurrentState;
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