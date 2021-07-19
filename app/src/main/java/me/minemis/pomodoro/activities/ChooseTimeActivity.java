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

    private Slider sliderWork, sliderBreak, sliderLongBreak, sliderRounds;
    private TextView txtFocusValue, txtBreakValue, txtLongBreakValue, txtRounds;
    private RoundManager roundManager;
    private int focusTime, breakTime, longBreakTime, numberOfRounds;
    private static boolean changed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_time);

        setMetrics();
        assignValues();
    }

    @Override
    protected void onStop() {
        changed = false;

        State state = roundManager.getCurrentState();

        switch (state) {

            case FOCUS:
                if ((int) sliderWork.getValue() != focusTime) {
                    changed = true;
                }   break;

            case SHORT_BREAK:
                if ((int) sliderBreak.getValue() != breakTime) {
                    changed = true;
                }   break;

            case LONG_BREAK:
                if ((int) sliderLongBreak.getValue() != longBreakTime) {
                    changed = true;
                }   break;
        }

        if ((int) sliderRounds.getValue() != numberOfRounds) {
            roundManager.resetCurrentRound();
        }

        if (changed) {
            MainActivity.getInstance().makeNewCountdownManager(state, false);
        }

        super.onStop();
    }

    private void setMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .8));
    }

    private void assignValues() {
        roundManager =      MainActivity.getRoundManager();

        focusTime =         roundManager.getValue(State.FOCUS);
        breakTime =         roundManager.getValue(State.SHORT_BREAK);
        longBreakTime =     roundManager.getValue(State.LONG_BREAK);
        numberOfRounds =    roundManager.getValue(State.ROUNDS);

        sliderWork =        findViewById(R.id.slider_work);
        sliderBreak =       findViewById(R.id.slider_break);
        sliderLongBreak =   findViewById(R.id.slider_long_brake);
        sliderRounds =      findViewById(R.id.slider_rounds);

        txtFocusValue =     findViewById(R.id.txt_slider_work_value);
        txtBreakValue =     findViewById(R.id.txt_slider_brake);
        txtLongBreakValue = findViewById(R.id.txt_slider_long_brake);
        txtRounds =         findViewById(R.id.txt_slider_rounds);

        txtFocusValue       .setText(String.valueOf(focusTime));
        sliderWork          .setValue(focusTime);

        txtBreakValue       .setText(String.valueOf(breakTime));
        sliderBreak         .setValue(breakTime);

        txtLongBreakValue   .setText(String.valueOf(longBreakTime));
        sliderLongBreak     .setValue(longBreakTime);

        txtRounds           .setText(String.valueOf(numberOfRounds));
        sliderRounds        .setValue(numberOfRounds);

        sliderWork          .addOnChangeListener(new SliderListener(this, State.FOCUS));
        sliderBreak         .addOnChangeListener(new SliderListener(this, State.SHORT_BREAK));
        sliderLongBreak     .addOnChangeListener(new SliderListener(this, State.LONG_BREAK));
        sliderRounds        .addOnChangeListener(new SliderListener(this, State.ROUNDS));
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public TextView getTxtValue(State state) {
        switch (state) {
            case FOCUS:         return txtFocusValue;
            case SHORT_BREAK:   return txtBreakValue;
            case LONG_BREAK:    return txtLongBreakValue;
            case ROUNDS:        return txtRounds;
        }
        return null;
    }
}
