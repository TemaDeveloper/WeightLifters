package com_gym.java_gym.weightlifters.database;

import androidx.annotation.NonNull;

public enum ConstantsDB {

    //database name

    WEIGHT_LIFTERS_DATABASE_NAME ("weight_lifters.db"),

    //day program exercise

    DAY_PROGRAM_EXERCISE_TABLE ("DAY_PROGRAM_EXERCISE_TABLE"),
    COLUMN_DAY_TITLE ("DAY_TITLE"),

    //week constants

    WEEK_TABLE ("WEEK_TABLE"),
    COLUMN_WEEK_ID ("WEEK_ID"),
    COLUMN_WEEK_DONE ("WEEK_DONE"),
    COLUMN_WEEK_SNAP ("WEEK_SNAP"),

    //program constants

    PROGRAM_TABLE ("PROGRAM_TABLE"),
    COLUMN_PROGRAM_ID ("PROGRAM_ID"),
    COLUMN_PROGRAM_TITLE ("PROGRAM_TITLE"),

    //exercise program constants

    PROGRAM_EXERCISE_TABLE ("PROGRAM_EXERCISE_TABLE"),
    COLUMN_INDEPENDENT_ID ("INDEPENDENT_EXERCISE_ID"),
    COLUMN_EXERCISE_ID ("EXERCISE_ID"),
    COLUMN_EXERCISE_TITLE ("EXERCISE_TITLE"),
    COLUMN_EXERCISE_REPETITION ("EXERCISE_REPETITION"),
    COLUMN_EXERCISE_WEIGHT ("EXERCISE_WEIGHT"),
    COLUMN_EXERCISE_SET ("EXERCISE_SET");

    private String databaseConstant;
    ConstantsDB(String databaseConstant){
        this.databaseConstant = databaseConstant;
    }

    public String getDatabaseConstant(){
        return databaseConstant;
    }

}
