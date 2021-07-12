package me.minemis.pomodoro.listeners;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.Time;
import me.minemis.pomodoro.activities.ChooseTimeActivity;
import me.minemis.pomodoro.activities.MainActivity;

public class SliderTimeListener implements Slider.OnChangeListener {

    private final ChooseTimeActivity chooseTimeActivity;
    private TextView txtSliderValue;

    public SliderTimeListener(ChooseTimeActivity chooseTimeActivity) {
        this.chooseTimeActivity = chooseTimeActivity;
        txtSliderValue = chooseTimeActivity.getTxtSliderValue();

    }

    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

        int intValue = (int) value;

        txtSliderValue.setText(String.valueOf(intValue));

        Time.setMinutes(intValue);

        MainActivity mainActivity = MainActivity.getInstance();
        mainActivity.callNewCDM();
    }
}
