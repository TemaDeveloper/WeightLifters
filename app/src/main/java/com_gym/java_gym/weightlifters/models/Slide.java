package com_gym.java_gym.weightlifters.models;

public class Slide {

    private String title, description, num;
    private int slideAnimation;

    public Slide(String title, String description, int slideAnimation, String num) {
        this.title = title;
        this.num = num;
        this.description = description;
        this.slideAnimation = slideAnimation;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScreenImg(int slideAnimation) {
        this.slideAnimation = slideAnimation;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getScreenImg() {
        return slideAnimation;
    }

}
