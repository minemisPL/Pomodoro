package me.minemis.pomodoro.listeners.choosetime;

import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.State;
import me.minemis.pomodoro.activities.ChooseTimeActivity;

public class SliderListener implements Slider.OnChangeListener {

    private final TextView txtSliderValue;
    private final RoundManager roundManager;
    private final State state;

    public SliderListener(ChooseTimeActivity chooseTimeActivity, State state) {
        this.txtSliderValue = chooseTimeActivity.getTxtValue(state);
        this.roundManager = chooseTimeActivity.getRoundManager();
        this.state = state;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
        int intValue = (int) value;

        txtSliderValue.setText(String.valueOf(intValue));
        roundManager.setValue(this.state, intValue);
    }
}
