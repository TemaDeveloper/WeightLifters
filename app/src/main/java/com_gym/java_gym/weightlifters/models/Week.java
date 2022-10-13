package com_gym.java_gym.weightlifters.models;

public class Week {

    private int numOfWeek;
    private String img, done;


    public Week(int numOfWeek, String done) {
        this.numOfWeek = numOfWeek;
        this.done = done;
    }

    public Week(int numOfWeek, String done, String img) {
        this.numOfWeek = numOfWeek;
        this.done = done;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNumOfWeek() {
        return numOfWeek;
    }

    public void setNumOfWeek(int numOfWeek) {
        this.numOfWeek = numOfWeek;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }
}
