package me.minemis.pomodoro.listeners;

import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.activities.MainActivity;

public class NextButtonListener implements View.OnClickListener {
    private final MainActivity mainActivity;
    private final TextView timerText;
    private final RoundManager roundManager;
    private final CountdownManager countdownManager;
    private final ProgressBar progressBar;
    private final ImageButton btnPlayPause;
    private static boolean canPass = true;

    public NextButtonListener(MainActivity mainActivity, RoundManager roundManager, CountdownManager countdownManager) {
        this.mainActivity = mainActivity;
        this.roundManager = roundManager;
        this.countdownManager = countdownManager;
        timerText = mainActivity.getTextViewTimer();
        progressBar = mainActivity.getProgressBar();
        btnPlayPause = mainActivity.getBtnStartPause();
    }


    @Override
    public void onClick(View v) {

        if (!canPass) {
            return;
        }

        canPass = false;

        int time = roundManager.getTime(roundManager.getNextState());

        if (!(countdownManager == null)) {
            countdownManager.pauseTimer();
        }

        //btnPlayPause.setImageDrawable();
        progressBar.setProgress(10000);
        timerText.setText(String.format(Locale.getDefault(), "%02d:00", time));
        System.out.print(String.format(Locale.getDefault(), "%02d:00", time) + "pizza");

        new Handler().postDelayed(() -> {
            MainActivity.getRoundManager().nextRound(true);
            canPass = true;
        }, 1000);

    }
}
