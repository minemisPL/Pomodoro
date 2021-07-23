package me.minemis.pomodoro.listeners.choosetime;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.State;
import me.minemis.pomodoro.activities.ChooseTimeActivity;
import me.minemis.pomodoro.activities.MainActivity;

public class ResetButtonListener implements View.OnClickListener {
    private final ChooseTimeActivity chooseTimeActivity;
    private final RoundManager roundManager;

    public ResetButtonListener(ChooseTimeActivity chooseTimeActivity) {
        this.chooseTimeActivity = chooseTimeActivity;
        this.roundManager = chooseTimeActivity.getRoundManager();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        roundManager.getValueMap().forEach((k, value) -> roundManager.setValue(k, k.getDefaultValue()));
        roundManager.resetCurrentRound();
        MainActivity.getInstance().makeNewCountdownManager(State.FOCUS, false);
        chooseTimeActivity.resetSliders();
    }
}
