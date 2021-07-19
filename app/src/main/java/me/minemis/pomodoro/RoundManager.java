package me.minemis.pomodoro;

import java.util.HashMap;
import java.util.Map;

import me.minemis.pomodoro.activities.MainActivity;

public class RoundManager {

    private final MainActivity mainActivity;
    private final Map<State, Integer> valueMap = new HashMap<>();
    private State currentState = State.SHORT_BREAK;
    private int currentRound = 1;
    private int numberOfSeries = 1;

    public RoundManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void nextRound(boolean setStart) {
        if (currentRound > getRealRounds()) {
            currentRound = 1;
        }

        System.out.println("Current round: " + currentRound);
        System.out.println("getValue(State.ROUNDS) result: " + getValue(State.ROUNDS));


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
        if (currentState == State.FOCUS) {
            currentRound = 0;
            return;
        }
        currentRound = 1;
    }

    public State getCurrentState() {
        return currentState;
    }

    private int getRealRounds() {
        return getValue(State.ROUNDS) * 2;
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