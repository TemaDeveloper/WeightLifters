package com_gym.java_gym.weightlifters.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com_gym.java_gym.weightlifters.models.Day;
import com_gym.java_gym.weightlifters.models.Exercise;
import com_gym.java_gym.weightlifters.models.Program;
import com_gym.java_gym.weightlifters.models.Week;

public class DatabaseHelper extends SQLiteOpenHelper{


    public DatabaseHelper(@Nullable Context context) {
        super(context, ConstantsDB.WEIGHT_LIFTERS_DATABASE_NAME.getDatabaseConstant(), null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String weekTableDescription = "CREATE TABLE " + ConstantsDB.WEEK_TABLE.getDatabaseConstant() + " (" +
                ConstantsDB.COLUMN_WEEK_ID.getDatabaseConstant() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantsDB.COLUMN_WEEK_SNAP.getDatabaseConstant() + " TEXT, " +
                ConstantsDB.COLUMN_WEEK_DONE.getDatabaseConstant() + " TEXT)";

        String programTableDescription = "CREATE TABLE " + ConstantsDB.PROGRAM_TABLE.getDatabaseConstant() + " (" +
                ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant() + " TEXT)";

        String programExerciseTableDescription = "CREATE TABLE " + ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant() + " (" +
                ConstantsDB.COLUMN_INDEPENDENT_ID.getDatabaseConstant() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantsDB.COLUMN_EXERCISE_ID.getDatabaseConstant() + " INTEGER, " +
                ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant() + " INTEGER, " +
                ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant() + " TEXT, " +
                ConstantsDB.COLUMN_EXERCISE_TITLE.getDatabaseConstant() + " TEXT, " +
                ConstantsDB.COLUMN_EXERCISE_WEIGHT.getDatabaseConstant() + " TEXT, " +
                ConstantsDB.COLUMN_EXERCISE_REPETITION.getDatabaseConstant() + " TEXT, " +
                ConstantsDB.COLUMN_EXERCISE_SET.getDatabaseConstant() + " TEXT)";

        String dayProgramExerciseTableDescription = "CREATE TABLE " + ConstantsDB.DAY_PROGRAM_EXERCISE_TABLE.getDatabaseConstant() + " (" +
                ConstantsDB.COLUMN_INDEPENDENT_ID.getDatabaseConstant() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantsDB.COLUMN_WEEK_ID.getDatabaseConstant() + " INTEGER, " +
                ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant() + " INTEGER, " +
                ConstantsDB.COLUMN_DAY_TITLE.getDatabaseConstant() + " TEXT, " +
                ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant() + " TEXT)";

        db.execSQL(programTableDescription);
        db.execSQL(dayProgramExerciseTableDescription);
        db.execSQL(weekTableDescription);
        db.execSQL(programExerciseTableDescription);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop table if particular table exists
        db.execSQL("DROP TABLE IF EXISTS " + ConstantsDB.PROGRAM_TABLE.getDatabaseConstant());
        db.execSQL("DROP TABLE IF EXISTS " + ConstantsDB.WEEK_TABLE.getDatabaseConstant());
        db.execSQL("DROP TABLE IF EXISTS " + ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant());
        db.execSQL("DROP TABLE IF EXISTS " + ConstantsDB.DAY_PROGRAM_EXERCISE_TABLE.getDatabaseConstant());
        //create table again
        onCreate(db);
    }

    public boolean addWeek(Week week){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValuesWeek = new ContentValues();

        contentValuesWeek.put(ConstantsDB.COLUMN_WEEK_DONE.getDatabaseConstant(), week.getDone());
        contentValuesWeek.put(ConstantsDB.COLUMN_WEEK_SNAP.getDatabaseConstant(), week.getImg());

        long insertionWeek = database.insert(ConstantsDB.WEEK_TABLE.getDatabaseConstant(), null, contentValuesWeek);
        if(insertionWeek == -1){
            return false;
        }else{
            return true;
        }

    }

    //update week
    public boolean updateWeek(String row_id, String check, String img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ConstantsDB.COLUMN_WEEK_DONE.getDatabaseConstant(), check);
        cv.put(ConstantsDB.COLUMN_WEEK_SNAP.getDatabaseConstant(), img);

        Cursor cursor = db.rawQuery("SELECT * FROM " + ConstantsDB.WEEK_TABLE.getDatabaseConstant() + " WHERE " + ConstantsDB.COLUMN_WEEK_ID.getDatabaseConstant() + "=?", new String[]{row_id});
        if (cursor.getCount() > 0) {
            long result = db.update(ConstantsDB.WEEK_TABLE.getDatabaseConstant(), cv, ConstantsDB.COLUMN_WEEK_ID.getDatabaseConstant() + "=?", new String[]{row_id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public List<Week> getEveryWeek(){
        List<Week> returningListWeeks = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String queryWeek = "SELECT * FROM " + ConstantsDB.WEEK_TABLE.getDatabaseConstant();
        Cursor cursor = database.rawQuery(queryWeek, null);
        if(cursor.moveToFirst()){
            do{
                int idWeek = cursor.getInt(0);
                String image = cursor.getString(1);
                String done = cursor.getString(2);
                Week week = new Week(idWeek, done, image);
                returningListWeeks.add(week);

            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return returningListWeeks;
    }

    public boolean addProgram(Program program) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant(), program.getNamingOfProgram());

        long insertionProgram = database.insert(ConstantsDB.PROGRAM_TABLE.getDatabaseConstant(), null, contentValues);

        if(insertionProgram == -1){
            return false;
        }else{
            return true;
        }

    }

    public boolean addExercise(Exercise exercise) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant(), exercise.getIdProgram());
        contentValues.put(ConstantsDB.COLUMN_EXERCISE_ID.getDatabaseConstant(), exercise.getIdEx());
        contentValues.put(ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant(), exercise.getTitleProgram());
        contentValues.put(ConstantsDB.COLUMN_EXERCISE_TITLE.getDatabaseConstant(), exercise.getEx());
        contentValues.put(ConstantsDB.COLUMN_EXERCISE_WEIGHT.getDatabaseConstant(), exercise.getWeight());
        contentValues.put(ConstantsDB.COLUMN_EXERCISE_SET.getDatabaseConstant(), exercise.getSets());
        contentValues.put(ConstantsDB.COLUMN_EXERCISE_REPETITION.getDatabaseConstant(), exercise.getRepetition());

        long insertionExercise = database.insert(ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant(), null, contentValues);

        if(insertionExercise == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean addDay(Day day){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValuesDay = new ContentValues();

        contentValuesDay.put(ConstantsDB.COLUMN_WEEK_ID.getDatabaseConstant(), day.getWeekId());
        contentValuesDay.put(ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant(), day.getIdProgram());
        contentValuesDay.put(ConstantsDB.COLUMN_DAY_TITLE.getDatabaseConstant(), day.getDayOfWeek());
        contentValuesDay.put(ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant(), day.getTitleProgram());

        long insertionDay = database.insert(ConstantsDB.DAY_PROGRAM_EXERCISE_TABLE.getDatabaseConstant(), null, contentValuesDay);
        if(insertionDay == -1){
            return false;
        }else{
            return true;
        }

    }

    public List<Day> getEveryDay(int idWeekIntent) {

        List<Day> returningList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String queryDay = "SELECT * FROM " + ConstantsDB.DAY_PROGRAM_EXERCISE_TABLE.getDatabaseConstant() + " WHERE " + ConstantsDB.COLUMN_WEEK_ID.getDatabaseConstant() + " == " + idWeekIntent;

        Cursor cursor = database.rawQuery(queryDay, null);
        if(cursor.moveToFirst()){
            do{
                int idDay = cursor.getInt(0);
                int idWeek = cursor.getInt(1);
                int idProgram = cursor.getInt(2);
                String dayOfWeek = cursor.getString(3);
                String titleProgram = cursor.getString(4);
                Day day = new Day(idDay, idWeek, idProgram, dayOfWeek, titleProgram);
                returningList.add(day);
            }while(cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return returningList;
    }


    public List<Program> getEveryProgram() {

        List<Program> returningList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String queryProgram = "SELECT * FROM " + ConstantsDB.PROGRAM_TABLE.getDatabaseConstant();

        Cursor cursor = database.rawQuery(queryProgram, null);
        if(cursor.moveToFirst()){
            do{
                int idProgram = cursor.getInt(0);
                String titleProgram = cursor.getString(1);
                Program program = new Program(idProgram, titleProgram);
                returningList.add(program);
            }while(cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return returningList;
    }

    public List<Exercise> getEveryExercise(int idExercise) {
        List<Exercise> returningList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String queryExercise = "SELECT * FROM " + ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant() + " WHERE " +
                ConstantsDB.COLUMN_EXERCISE_ID.getDatabaseConstant() + " == " + idExercise;

        Cursor cursor = database.rawQuery(queryExercise, null);
        if(cursor.moveToFirst()){
            do{
                int idIndependent = cursor.getInt(0);
                int idProgram = cursor.getInt(1);
                int idEx = cursor.getInt(2);
                String titleProgram = cursor.getString(3);
                String titleExercise = cursor.getString(4);
                String weight = cursor.getString(5);
                String set = cursor.getString(6);
                String repetition = cursor.getString(7);

                Exercise exercise = new Exercise(idIndependent, idProgram, idEx, titleProgram, titleExercise, weight, set, repetition);
                returningList.add(exercise);

            }while(cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return returningList;
    }

    //update program
    public boolean updateProgram(String row_id, String title){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ConstantsDB.COLUMN_PROGRAM_TITLE.getDatabaseConstant(), title);

        Cursor cursor = db.rawQuery("SELECT * FROM " + ConstantsDB.PROGRAM_TABLE.getDatabaseConstant() + " WHERE " + ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant() + "=?", new String[]{row_id});
        if (cursor.getCount() > 0) {
            long result = db.update(ConstantsDB.PROGRAM_TABLE.getDatabaseConstant(), cv, ConstantsDB.COLUMN_PROGRAM_ID.getDatabaseConstant() + "=?", new String[]{row_id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

     //update exercise
     public boolean updateExercise(String row_id, String titleEx, String weightEx, String setsEx, String repetitionEx) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ConstantsDB.COLUMN_EXERCISE_TITLE.getDatabaseConstant(), titleEx);
        cv.put(ConstantsDB.COLUMN_EXERCISE_WEIGHT.getDatabaseConstant(), weightEx);
        cv.put(ConstantsDB.COLUMN_EXERCISE_SET.getDatabaseConstant(), setsEx);
        cv.put(ConstantsDB.COLUMN_EXERCISE_REPETITION.getDatabaseConstant(), repetitionEx);

        Cursor cursor = db.rawQuery("SELECT * FROM " + ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant() + " WHERE " + ConstantsDB.COLUMN_INDEPENDENT_ID.getDatabaseConstant() + "=?", new String[]{row_id});
        if (cursor.getCount() > 0) {
            long result = db.update(ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant(), cv, ConstantsDB.COLUMN_INDEPENDENT_ID.getDatabaseConstant() + "=?", new String[]{row_id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //delete common
    public boolean deleteExercise(Exercise exercise) {
        //find item data model in the database. If it found, delete it and return true
        //if it is not found return false

        SQLiteDatabase db = this.getWritableDatabase();
        String queryExerciseDelete = "DELETE FROM " + ConstantsDB.PROGRAM_EXERCISE_TABLE.getDatabaseConstant() + " WHERE " + ConstantsDB.COLUMN_INDEPENDENT_ID.getDatabaseConstant() + " = " + exercise.getIdIndependentEx();
        Cursor cursor = db.rawQuery(queryExerciseDelete, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

}
