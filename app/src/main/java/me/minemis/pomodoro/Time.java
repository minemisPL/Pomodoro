package me.minemis.pomodoro;

public class Time {
    private static int minutes = 9;

    public static int getMinutes() {
        return minutes;
    }

    public static void setMinutes(int minutes) {
        Time.minutes = minutes;
    }
}
