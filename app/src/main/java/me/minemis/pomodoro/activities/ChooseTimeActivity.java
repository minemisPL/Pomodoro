package me.minemis.pomodoro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import me.minemis.pomodoro.R;
import me.minemis.pomodoro.listeners.SliderTimeListener;

public class ChooseTimeActivity extends AppCompatActivity {

    private Slider slider;
    private TextView txtSliderValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_time);
        setMetrics();

        slider = findViewById(R.id.slider_work);
        txtSliderValue = findViewById(R.id.txt_slider_work_value);

        slider.addOnChangeListener(new SliderTimeListener(this));

        Intent intent = getIntent();
        intent.putExtra("work_time", 60);
    }

    private void setMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    public TextView getTxtSliderValue() {
        return txtSliderValue;
    }
}
