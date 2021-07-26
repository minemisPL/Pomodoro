package me.minemis.pomodoro.listeners.choosetime;

import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.SettingOption;
import me.minemis.pomodoro.activities.ChooseTimeActivity;

public class SliderListener implements Slider.OnChangeListener {

    private final TextView txtSliderValue;
    private final RoundManager roundManager;
    private final SettingOption settingOption;

    public SliderListener(ChooseTimeActivity chooseTimeActivity, SettingOption settingOption) {
        this.txtSliderValue = chooseTimeActivity.getTxtValue(settingOption);
        this.roundManager = chooseTimeActivity.getRoundManager();
        this.settingOption = settingOption;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
        int intValue = (int) value;

        txtSliderValue.setText(String.valueOf(intValue));
        roundManager.setValue(this.settingOption, intValue);
    }
}
