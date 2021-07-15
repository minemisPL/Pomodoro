package me.minemis.pomodoro.listeners;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.activities.ChooseTimeActivity;

public class SliderBreakListener implements Slider.OnChangeListener {

    private final TextView txtSliderValue;
    private final RoundManager roundManager;

    public SliderBreakListener(ChooseTimeActivity chooseTimeActivity) {
        txtSliderValue = chooseTimeActivity.getTxtBreakValue();
        roundManager = chooseTimeActivity.getRoundManager();
    }

    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

        int intValue = (int) value;

        txtSliderValue.setText(String.valueOf(intValue));

        roundManager.setShortBreakTime(intValue);
    }
}
