package me.minemis.pomodoro;

import me.minemis.pomodoro.activities.MainActivity;

public class RoundManager {

    private final MainActivity mainActivity;
    private int numberOfRounds = 4;
    private int realRounds = 4 * 2;
    private int currentRound = 1;
    private State currentState = State.FOCUS;
    private int focusTime = 25;
    private int shortBreakTime = 5;
    private int longBreakTime = 10;

    public RoundManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public int getFocusTime() {
        return focusTime;
    }

    public void nextRound(boolean setStart) {
        if (currentRound > realRounds) {
            currentRound = 1;
        }

        if (currentRound == realRounds) {
            currentState = State.LONG_BREAK;
        } else if (currentRound %2 == 1)  {
            currentState = State.FOCUS;
        } else {
            currentState = State.SHORT_BREAK;
        }

        mainActivity.makeNewCountdownManager(currentState, setStart);
        currentRound++;
    }

    public State getNextState() {
        if (currentState == State.SHORT_BREAK || currentState == State.LONG_BREAK) {
            return State.FOCUS;
        }
        if (currentRound == realRounds) {
            return State.LONG_BREAK;
        }
        return State.SHORT_BREAK;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
        this.realRounds = numberOfRounds * 2;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setFocusTime(int focusTime) {
        this.focusTime = focusTime;
    }

    public void setShortBreakTime(int shortBreakTime) {
        this.shortBreakTime = shortBreakTime;
    }

    public int getTime(State state) {
        switch (state) {
            case FOCUS: return focusTime;
            case SHORT_BREAK: return shortBreakTime;
            case LONG_BREAK: return longBreakTime;
        }
        return 1;
    }
}
