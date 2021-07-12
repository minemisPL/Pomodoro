package me.minemis.pomodoro;

import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.OnLifecycleEvent;

import java.util.Locale;

import me.minemis.pomodoro.activities.MainActivity;

public class CountdownManager {

    private final long ORIGIN_TIME_IN_MILLIS;

    private final MainActivity mainActivity;

    private TextView text1, text2, text3;

    private CountDownTimer countDownTimer;

    private final ProgressBar progressBar;
    private double  progress = 10000;
    private final double progressPart;

    private boolean isRunning;

    private long timeLeftInMillis;
    private final TextView textViewCountdown;

    public CountdownManager(MainActivity mainActivity, ProgressBar progressBar, TextView textViewCountdown, long timeLeftInMillis) {
        this.mainActivity = mainActivity;
        this.progressBar = progressBar;
        this.textViewCountdown = textViewCountdown;
        this.timeLeftInMillis = timeLeftInMillis;
        this.ORIGIN_TIME_IN_MILLIS = timeLeftInMillis;
        updateCountdownText();
        progressBar.setProgress(10000);
        progressPart = 10000 / ((double) timeLeftInMillis / 1000);
        text1 = mainActivity.getText1();
        text2 = mainActivity.getText2();
        text3 = mainActivity.getText3();
    }

    public void startTimer() {
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

            }
        }.start();

        isRunning = true;
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountdown.setText(timeLeftFormatted);
    }

    public void pauseTimer() {
        try {
            countDownTimer.cancel();
        } catch (NullPointerException ignored) {}

        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void resetTimer() {
        pauseTimer();
        timeLeftInMillis = ORIGIN_TIME_IN_MILLIS;
        updateCountdownText();
        progressBar.setProgress(10000);
        progress = 10000;
    }

}
