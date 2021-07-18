package me.minemis.pomodoro.listeners.main;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.Locale;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.State;
import me.minemis.pomodoro.activities.MainActivity;

public class NextButtonListener implements View.OnClickListener {

    private final TextView timerText;
    private final TextView txtState;
    private final RoundManager roundManager;
    private final CountdownManager countdownManager;
    private final ProgressBar progressBar;
    private static boolean canPass = true;

    public NextButtonListener(MainActivity mainActivity) {
        this.roundManager = MainActivity.getRoundManager();
        this.countdownManager = mainActivity.getCountdownManager();
        this.timerText = mainActivity.getTextViewTimer();
        this.progressBar = mainActivity.getProgressBar();
        this.txtState = mainActivity.getTxtCurrentState();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (!canPass) {
            return;
        }

        State state = roundManager.getNextState();

        next(state);
        setStateText(state);
    }

    private void next(State state) {
        canPass = false;

        int time = roundManager.getTime(state);

        if (!(countdownManager == null)) {
            countdownManager.pauseTimer();
        }

        progressBar.setProgress(10000);
        timerText.setText(String.format(Locale.getDefault(), "%02d:00", time));
        System.out.print(String.format(Locale.getDefault(), "%02d:00", time) + "pizza");

        new Handler().postDelayed(() -> {
            MainActivity.getRoundManager().nextRound(true);
            canPass = true;
        }, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setStateText(State state) {
        txtState.setText(state.getStringValue());
    }
}
