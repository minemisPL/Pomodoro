package me.minemis.pomodoro.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
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
    private TextView txtWhichRound;
    private TextView txtTotalRounds;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CountdownManager countdownManager;

    @SuppressLint("StaticFieldLeak")
    private static MainActivity instance;
    private static RoundManager roundManager;

    public MainActivity() {
        super();
        instance = this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignValues();
        loadSettings();

        roundManager.nextRound(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStop() {
        roundManager.getValueMap().forEach(this::putValueToEditor);
        editor.apply();

        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void makeNewCountdownManager(State state, boolean setStart) {

        if (!(countdownManager == null)){
            countdownManager.pauseTimer();
            countdownManager = null;
        }

        int time =          roundManager.getValue(state);

        countdownManager =  new CountdownManager(this, time * 60 * 1000);

        btnNext             .setOnClickListener(new NextButtonListener(MainActivity.this));
        btnStartPause       .setOnClickListener(new ButtonStartPauseListener(MainActivity.this));
        btnReset            .setOnClickListener(new ButtonResetListener(MainActivity.this));

        txtCurrentState.setText(state.getStringValue());

        txtTotalRounds.setText(String.valueOf(roundManager.getTotalRounds()));
        txtWhichRound.setText(roundManager.getWhichRound());

        if (setStart) {
            btnStartPause   .setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
            countdownManager.startTimer();
            return;
        }
        btnStartPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadSettings() {
        roundManager.getValueMap().forEach((k, v) -> setRMValues(k));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setRMValues(State state) {
        roundManager.setValue(state, sharedPreferences.getInt(state.getStringValue(), state.getDefaultValue()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void putValueToEditor(State state, Integer integer) {
        editor.putInt(state.getStringValue(), integer);
    }

    @SuppressLint("CommitPrefEdits")
    private void assignValues() {
        roundManager =      new RoundManager(this);

        sharedPreferences = getSharedPreferences("PomodoroTimes", MODE_PRIVATE);

        editor =            sharedPreferences.edit();

        textViewTimer =     findViewById(R.id.txt_timer);
        btnStartPause =     findViewById(R.id.btn_start);
        btnReset =          findViewById(R.id.btn_reset);
        progressBar =       findViewById(R.id.progress_bar);
        btnNext =           findViewById(R.id.btn_next_phase);
        txtCurrentState =   findViewById(R.id.txt_current_state);
        txtWhichRound =     findViewById(R.id.txt_which_round);
        txtTotalRounds =    findViewById(R.id.txt_total_value);

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

    public TextView getTxtWhichRound() {
        return txtWhichRound;
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