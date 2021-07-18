package me.minemis.pomodoro.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.R;
import me.minemis.pomodoro.RoundManager;
import me.minemis.pomodoro.State;
import me.minemis.pomodoro.listeners.choosetime.SliderListener;

public class ChooseTimeActivity extends AppCompatActivity {

    private Slider sliderWork, sliderBreak, sliderLongBreak;
    private TextView txtFocusValue, txtBreakValue, txtLongBreakValue;
    private RoundManager roundManager;
    private int focusTime, breakTime, longBreakTime;
    private static boolean isChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_time);

        setMetrics();
        assignValues();
    }

    @Override
    protected void onStop() {

        isChanged = false;

        State state = roundManager.getCurrentState();

        switch (state) {

            case FOCUS:
                if ((int) sliderWork.getValue() != focusTime) {
                    isChanged = true;
                }   break;

            case SHORT_BREAK:
                if ((int) sliderBreak.getValue() != breakTime) {
                    isChanged = true;
                }   break;

            case LONG_BREAK:
                if ((int) sliderLongBreak.getValue() != longBreakTime) {
                    isChanged = true;
                }   break;
        }

        if (isChanged) {
            MainActivity.getInstance().makeNewCountdownManager(state, false);
        }

        super.onStop();
    }

    private void setMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    private void assignValues() {
        roundManager =      MainActivity.getRoundManager();

        focusTime =         roundManager.getTime(State.FOCUS);
        breakTime =         roundManager.getTime(State.SHORT_BREAK);
        longBreakTime =     roundManager.getTime(State.LONG_BREAK);


        sliderWork =        findViewById(R.id.slider_work);
        sliderBreak =       findViewById(R.id.slider_break);
        sliderLongBreak =   findViewById(R.id.slider_long_brake);

        txtFocusValue =     findViewById(R.id.txt_slider_work_value);
        txtBreakValue =     findViewById(R.id.txt_slider_brake);
        txtLongBreakValue = findViewById(R.id.txt_slider_long_brake);

        txtFocusValue       .setText(String.valueOf(focusTime));
        sliderWork          .setValue(focusTime);

        txtBreakValue       .setText(String.valueOf(breakTime));
        sliderBreak         .setValue(breakTime);

        txtLongBreakValue   .setText(String.valueOf(longBreakTime));
        sliderLongBreak     .setValue(longBreakTime);

        sliderWork          .addOnChangeListener(new SliderListener(this, State.FOCUS));
        sliderBreak         .addOnChangeListener(new SliderListener(this, State.SHORT_BREAK));
        sliderLongBreak     .addOnChangeListener(new SliderListener(this, State.LONG_BREAK));
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public TextView getTxtValue(State state) {
        switch (state) {
            case FOCUS:         return txtFocusValue;
            case SHORT_BREAK:   return txtBreakValue;
            case LONG_BREAK:    return txtLongBreakValue;
        }
        return null;
    }
}
