package com_gym.java_gym.weightlifters.models;

public class Program {

    private int id;
    private String namingOfProgram;

    public Program(String namingOfProgram) {
        this.namingOfProgram = namingOfProgram;
    }

    public Program(int id, String namingOfProgram) {
        this.namingOfProgram = namingOfProgram;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamingOfProgram() {
        return namingOfProgram;
    }

    public void setNamingOfProgram(String namingOfProgram) {
        this.namingOfProgram = namingOfProgram;
    }
}
