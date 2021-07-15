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
import me.minemis.pomodoro.listeners.SliderBreakListener;
import me.minemis.pomodoro.listeners.SliderFocusListener;

public class ChooseTimeActivity extends AppCompatActivity {

    private Slider sliderWork, sliderBreak;
    private TextView txtFocusValue;
    private TextView txtBreakValue;
    private RoundManager roundManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_time);
        setMetrics();

        roundManager = MainActivity.getRoundManager();

        sliderWork = findViewById(R.id.slider_work);
        sliderBreak = findViewById(R.id.slider_break);
        txtFocusValue = findViewById(R.id.txt_slider_work_value);
        txtBreakValue = findViewById(R.id.txt_slider_brake);

        txtFocusValue.setText(String.valueOf(roundManager.getFocusTime()));
        sliderWork.setValue(roundManager.getFocusTime());

        txtBreakValue.setText(String.valueOf(roundManager.getTime(State.SHORT_BREAK)));
        sliderBreak.setValue(roundManager.getTime(State.SHORT_BREAK));

        sliderWork.addOnChangeListener(new SliderFocusListener(this));

        sliderBreak.addOnChangeListener(new SliderBreakListener(this));

    }

    @Override
    protected void onStop() {
        State state = MainActivity.getRoundManager().getCurrentState();
        MainActivity.getInstance().makeNewCountdownManager(state, false);

        super.onStop();
    }

    private void setMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public TextView getTxtFocusValue() {
        return txtFocusValue;
    }

    public TextView getTxtBreakValue() {
        return txtBreakValue;
    }
}
