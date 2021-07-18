package me.minemis.pomodoro;

import java.util.HashMap;
import java.util.Map;

import me.minemis.pomodoro.activities.MainActivity;

public class RoundManager {

    private final MainActivity mainActivity;
    private int numberOfRounds = 4;
    private int currentRound = 1;
    private State currentState = State.SHORT_BREAK;
    private final Map<State, Integer> timeMap = new HashMap<>();

    public RoundManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void nextRound(boolean setStart) {
        if (currentRound > getRealRounds()) {
            currentRound = 1;
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

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public State getCurrentState() {
        return currentState;
    }

    int getRealRounds() {
        return numberOfRounds * 2;
    }

    public void setTime(State state, int value) {
        timeMap.put(state, value);
    }

    public int getTime(State state) {
        Integer value = timeMap.get(state);

        if (value == null) {
            return state.getDefaultValue();
        }

        return value;
    }
}