package com_gym.java_gym.weightlifters.models;

public class Progress {

    private int idWeek;
    private float progress;
    private int set, rep, weight;

    public Progress(int idWeek, float progress) {
        this.idWeek = idWeek;
        this.progress = progress;
    }

    public Progress(int idWeek, float progress, int set, int rep, int weight) {
        this.idWeek = idWeek;
        this.progress = progress;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
    }

    public int getIdWeek() {
        return idWeek;
    }

    public void setIdWeek(int idWeek) {
        this.idWeek = idWeek;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
