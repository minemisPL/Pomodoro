package me.minemis.pomodoro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.minemis.pomodoro.activities.MainActivity;

public class RoundManager {

    private final MainActivity mainActivity;
    private final Map<SettingOption, Integer> settings = new HashMap<>();
    private SettingOption currentState = SettingOption.SHORT_BREAK;
    private int currentRound = 1;
    private int totalRounds = 0;
    private int realRound = 0;

    public RoundManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextRound(boolean setStart) {

        //kinda shitty ale nie mamy czasu na nic lepszego

        if (currentRound > getRealRounds()) {
            currentRound = 1;
        }

        if (currentState == SettingOption.LONG_BREAK) {
            realRound = 0;
        }

        if (realRound > getRealRounds()) {
            realRound = 0;
        }

        if (currentState != SettingOption.FOCUS) {
            realRound++;
            totalRounds++;
        }

        currentState = getNextState();
        mainActivity.makeNewCountdownManager(currentState, setStart);
        currentRound++;

    }

    public SettingOption getNextState() {
        if (currentState == SettingOption.SHORT_BREAK || currentState == SettingOption.LONG_BREAK) {
            return SettingOption.FOCUS;
        }

        if (currentRound == getRealRounds()) {
            return SettingOption.LONG_BREAK;
        }

        return SettingOption.SHORT_BREAK;
    }

    public void resetCurrentRound() {
        currentRound = currentState == SettingOption.FOCUS ? 1 : 0;
    }
    public void resetRealRound() {
        if (realRound * 2 > getRealRounds()){
            realRound = 1;
        }
    }

    public Map<SettingOption, Integer> getSettings() {
        return Collections.unmodifiableMap(settings);
    }

    public SettingOption getCurrentState() {
        return currentState;
    }

    private int getRealRounds() {
        return getValue(SettingOption.ROUNDS) * 2;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public String getWhichRound() {
        return realRound + "/" + getValue(SettingOption.ROUNDS);
    }

    public void setValue(SettingOption settingOption, int value) {
        settings.put(settingOption, value);
    }

    public int getValue(SettingOption settingOption) {
        Integer value = settings.get(settingOption);

        if (value == null) {
            int defaultValue = settingOption.getDefaultValue();
            settings.put(settingOption, defaultValue);
            return defaultValue;
        }

        return value;
    }
}