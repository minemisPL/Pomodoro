package me.minemis.pomodoro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.R;
import me.minemis.pomodoro.Time;
import me.minemis.pomodoro.listeners.ImageButtonResetListener;
import me.minemis.pomodoro.listeners.ImageButtonStartPauseListener;
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

    public MainActivity() {
        super();
        instance = this;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.txt_timer);
        btnStartPause = findViewById(R.id.btn_start);
        btnReset = findViewById(R.id.btn_reset);
        progressBar = findViewById(R.id.progress_bar);
        btnNext = findViewById(R.id.btn_next_phase);

        text1 = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);

        makeNewCountdownManager(25);

        Time.setMinutes(40);

        btnNext.setOnClickListener(v -> callNewCDM());
    }

    private void makeNewCountdownManager(int minutes) {

        countdownManager = new CountdownManager(this, progressBar, textViewTimer, minutes * 60 * 1000); //1500000

        btnStartPause.setOnClickListener(new ImageButtonStartPauseListener(this, countdownManager));
        btnReset.setOnClickListener(new ImageButtonResetListener(this, countdownManager));

        textViewTimer.setOnClickListener(new TextTimerListener(this));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void callNewCDM() {
        countdownManager.resetTimer();
        countdownManager = null;
        btnStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
        makeNewCountdownManager(Time.getMinutes());
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
}