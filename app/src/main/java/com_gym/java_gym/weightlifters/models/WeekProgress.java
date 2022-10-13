package com_gym.java_gym.weightlifters.models;

public class WeekProgress {

    private int numOfWeek;
    private int progress;

    public WeekProgress(int numOfWeek, int progress) {
        this.numOfWeek = numOfWeek;
        this.progress = progress;
    }

    public int getNumOfWeek() {
        return numOfWeek;
    }

    public void setNumOfWeek(int numOfWeek) {
        this.numOfWeek = numOfWeek;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
