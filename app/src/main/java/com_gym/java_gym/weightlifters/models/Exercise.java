package com_gym.java_gym.weightlifters.models;

public class Exercise {

    private int idIndependentEx, idEx, idProgram;
    private String titleProgram, ex, repetition, sets, weight;

    public Exercise(int idIndependentEx, int idProgram, int idEx, String titleProgram, String ex, String weight, String sets, String repetition) {
        this.idEx = idEx;
        this.idIndependentEx = idIndependentEx;
        this.idProgram = idProgram;
        this.titleProgram = titleProgram;
        this.ex = ex;
        this.repetition = repetition;
        this.sets = sets;
        this.weight = weight;
    }

    public Exercise(String ex, String repetition, String sets, String weight) {
        this.ex = ex;
        this.repetition = repetition;
        this.sets = sets;
        this.weight = weight;
    }

    public int getIdIndependentEx() {
        return idIndependentEx;
    }

    public void setIdIndependentEx(int idIndependentEx) {
        this.idIndependentEx = idIndependentEx;
    }

    public int getIdEx() {
        return idEx;
    }

    public void setIdEx(int idEx) {
        this.idEx = idEx;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public String getTitleProgram() {
        return titleProgram;
    }

    public void setTitleProgram(String titleProgram) {
        this.titleProgram = titleProgram;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }
}
