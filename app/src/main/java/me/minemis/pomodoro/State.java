package me.minemis.pomodoro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Supplier;

import me.minemis.pomodoro.activities.MainActivity;

public enum State {
    FOCUS(25, () -> MainActivity.getInstance().getResources().getString(R.string.focus)),
    SHORT_BREAK(5, () -> MainActivity.getInstance().getResources().getString(R.string.short_brake)),
    LONG_BREAK(10, () -> MainActivity.getInstance().getResources().getString(R.string.long_brake)),
    ROUNDS(4, () -> MainActivity.getInstance().getResources().getString(R.string.rounds));

    private final int defaultValue;
    private final Supplier<String> supplier;

    State(int defaultValue, Supplier<String> supplier) {
        this.defaultValue = defaultValue;
        this.supplier = supplier;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getStringValue() {
        return supplier.get();
    }
}