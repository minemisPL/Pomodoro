package me.minemis.pomodoro.listeners.main;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageButton;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.activities.MainActivity;
import me.minemis.pomodoro.R;

public class ButtonStartPauseListener implements View.OnClickListener {

    private final MainActivity mainActivity;
    private final CountdownManager countdownManager;

    public ButtonStartPauseListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.countdownManager = mainActivity.getCountdownManager();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {

        ImageButton button = (ImageButton) v;

        if (countdownManager.isRunning()){
            countdownManager.pauseTimer();
            button.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_play_arrow));
            return;
        }

        countdownManager.startTimer();
        button.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_pause));
    }
}
