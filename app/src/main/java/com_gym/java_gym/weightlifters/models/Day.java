package com_gym.java_gym.weightlifters.models;

import java.util.ArrayList;

public class Day {

    private int idIndependentEx, idProgram, weekId;
    private String dayOfWeek, titleProgram;

    public Day(int idIndependentEx, int weekId, int idProgram, String dayOfWeek, String titleProgram) {
        this.idIndependentEx = idIndependentEx;
        this.idProgram = idProgram;
        this.weekId = weekId;
        this.dayOfWeek = dayOfWeek;
        this.titleProgram = titleProgram;
    }

    public Day() {
    }

    public int getIdIndependentEx() {
        return idIndependentEx;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public int getWeekId() {
        return weekId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTitleProgram() {
        return titleProgram;
    }
}
