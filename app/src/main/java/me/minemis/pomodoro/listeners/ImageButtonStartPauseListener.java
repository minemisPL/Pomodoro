package me.minemis.pomodoro.listeners;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageButton;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.activities.MainActivity;
import me.minemis.pomodoro.R;

public class ImageButtonStartPauseListener implements View.OnClickListener {

    private final MainActivity mainActivity;
    private final CountdownManager countdownManager;
    private final ImageButton btnStartPause;

    public ImageButtonStartPauseListener(MainActivity mainActivity, CountdownManager countdownManager) {
        this.mainActivity = mainActivity;
        this.countdownManager = countdownManager;

        btnStartPause = mainActivity.getBtnStartPause();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {

        if (countdownManager.isRunning()){
            countdownManager.pauseTimer();
            btnStartPause.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_play_arrow));
            return;
        }

        countdownManager.startTimer();
        btnStartPause.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_pause));
    }
}
