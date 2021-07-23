package me.minemis.pomodoro;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import me.minemis.pomodoro.activities.MainActivity;

public class RoundManager {

    private final MainActivity mainActivity;
    private final Map<State, Integer> valueMap = new HashMap<>();
    private State currentState = State.SHORT_BREAK;
    private int currentRound = 1;
    private int totalRounds = 0;
    private int realRound = 0;

    public RoundManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        putDefaults(State.FOCUS, State.SHORT_BREAK, State.LONG_BREAK, State.ROUNDS);
    }

    private void putDefaults(State... states) {
        for (State state : states) {
            valueMap.put(state, state.getDefaultValue());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextRound(boolean setStart) {

        //kinda shitty ale nie mamy czasu na nic lepszego

        if (currentRound > getRealRounds()) {
            currentRound = 1;
        }

        if (currentState == State.LONG_BREAK) {
            realRound = 0;
        }

        if  (realRound > getRealRounds()) {
            realRound = 0;
        }

        if (currentState != State.FOCUS) {
            realRound++;
            totalRounds++;
        }

        currentState = getNextState();
        mainActivity.makeNewCountdownManager(currentState, setStart);
        currentRound++;

    }

    public State getNextState() {
        if (currentState == State.SHORT_BREAK || currentState == State.LONG_BREAK) {
            return State.FOCUS;
        }

        if (currentRound == getRealRounds()) {
            return State.LONG_BREAK;
        }

        return State.SHORT_BREAK;
    }

    public void resetCurrentRound() {
        currentRound = currentState == State.FOCUS ? 1 : 0;
    }
    public void resetRealRound() {
        if (realRound * 2 > getRealRounds()){
            realRound = 1;
        }
    }

    public Map<State, Integer> getValueMap() {
        return valueMap;
    }

    public State getCurrentState() {
        return currentState;
    }

    private int getRealRounds() {
        return getValue(State.ROUNDS) * 2;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public String getWhichRound() {
        return realRound + "/" + getValue(State.ROUNDS);
    }

    public void setValue(State state, int value) {
        valueMap.put(state, value);
    }

    public int getValue(State state) {
        Integer value = valueMap.get(state);

        if (value == null) {
            return state.getDefaultValue();
        }
        return value;
    }
}