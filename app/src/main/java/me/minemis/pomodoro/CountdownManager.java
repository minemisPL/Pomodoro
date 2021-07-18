package me.minemis.pomodoro;

import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import me.minemis.pomodoro.activities.MainActivity;

public class CountdownManager {

    private final static String FORMAT = "%02d:%02d";

    private final RoundManager roundManager;
    private final ProgressBar progressBar;
    private final long ORIGIN_TIME_IN_MILLIS;
    private final double progressPart;

    private CountDownTimer countDownTimer;
    private double progress = 10000;
    private long timeLeftInMillis;
    private boolean isRunning;

    private final TextView textViewCountdown;
    private final TextView text1, text2, text3;

    public CountdownManager(MainActivity mainActivity, long timeLeftInMillis) {
        this.progressBar = mainActivity.getProgressBar();
        this.textViewCountdown = mainActivity.getTextViewTimer();
        this.ORIGIN_TIME_IN_MILLIS = timeLeftInMillis;
        this.progressPart = 10000 / ((double) ORIGIN_TIME_IN_MILLIS / 1000);
        this.timeLeftInMillis = timeLeftInMillis;
        this.roundManager = MainActivity.getRoundManager();

        updateCountdownText();
        this.progressBar.setProgress(10000);
        this.text1 = mainActivity.getText1();
        this.text2 = mainActivity.getText2();
        this.text3 = mainActivity.getText3();
    }

    public void startTimer() {
        updateCountdownText();

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();

                text1.setText(String.valueOf(progressPart));

                text2.setText(String.valueOf(timeLeftInMillis));

                progress = progress - progressPart;

                text3.setText(String.valueOf(progress));

                progressBar.setProgress((int) progress);
            }

            @Override
            public void onFinish() {
                roundManager.nextRound(true);
            }
        }.start();

        isRunning = true;
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), FORMAT, minutes, seconds);

        textViewCountdown.setText(timeLeftFormatted);
    }

    public void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        isRunning = false;
    }

    public void resetTimer() {
        pauseTimer();
        timeLeftInMillis = ORIGIN_TIME_IN_MILLIS;
        updateCountdownText();
        progressBar.setProgress(10000);
        progress = 10000;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
