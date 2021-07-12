package me.minemis.pomodoro.listeners;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageButton;

import me.minemis.pomodoro.CountdownManager;
import me.minemis.pomodoro.R;
import me.minemis.pomodoro.activities.MainActivity;

public class ImageButtonResetListener implements View.OnClickListener {

    private final MainActivity mainActivity;
    private final CountdownManager countdownManager;
    private final ImageButton btnStartPause;

    public ImageButtonResetListener(MainActivity mainActivity, CountdownManager countdownManager) {
        this.mainActivity = mainActivity;
        this.countdownManager = countdownManager;

        btnStartPause = mainActivity.getBtnStartPause();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        countdownManager.resetTimer();
        btnStartPause.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_play_arrow));
    }
}
