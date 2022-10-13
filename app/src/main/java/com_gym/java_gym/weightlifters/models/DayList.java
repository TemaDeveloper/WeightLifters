package com_gym.java_gym.weightlifters.models;

import java.util.List;

public class DayList {

    private List<Exercise> exerciseList;
    private List<Day> days;

    public DayList(List<Exercise> exerciseList, List<Day> days) {
        this.exerciseList = exerciseList;
        this.days = days;
    }

    public List<Day> getDays() {
        return days;
    }

    public DayList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}
