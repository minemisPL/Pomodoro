package me.minemis.pomodoro.listeners.choosetime;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.State;
import me.minemis.pomodoro.activities.ChooseTimeActivity;

public class SliderListener implements Slider.OnChangeListener {

    private final TextView txtSliderValue;
    private final RoundManager roundManager;
    private final State state;

    public SliderListener(ChooseTimeActivity chooseTimeActivity, State state) {
        txtSliderValue = chooseTimeActivity.getTxtValue(state);
        roundManager = chooseTimeActivity.getRoundManager();
        this.state = state;
    }

    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

        int intValue = (int) value;

        txtSliderValue.setText(String.valueOf(intValue));
        roundManager.setTime(state ,intValue);
    }
}
