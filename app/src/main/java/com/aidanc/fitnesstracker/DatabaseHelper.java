package com.aidanc.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fitness_data";
    private static final String WORKOUT_TABLE = "workouts";
    private static final String EXERCISE_TABLE = "exercises";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createWorkoutTableQuery = "CREATE TABLE IF NOT EXISTS " + WORKOUT_TABLE + "(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "date DATETIME," +
                "UNIQUE(date));";

        sqLiteDatabase.execSQL(createWorkoutTableQuery);

        String createExerciseTableQuery = "CREATE TABLE IF NOT EXISTS " + EXERCISE_TABLE + "(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "name VARCHAR(30)," +
                "weight INTEGER," +
                "sets INTEGER," +
                "date DATETIME);";

        sqLiteDatabase.execSQL(createExerciseTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addWorkout() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("date", new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));

        db.insert(WORKOUT_TABLE, null, values);
        db.close();
    }

    public void addExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", exercise.name);
        values.put("weight", exercise.weight);
        values.put("sets", exercise.sets);
        values.put("date", new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));

        db.insert(EXERCISE_TABLE, null, values);
        db.close();
    }

    public ArrayList<Workout> getAllWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();

        String getAllQuery = "SELECT * FROM " + WORKOUT_TABLE + ";";
        Cursor cursor = db.rawQuery(getAllQuery, null);

        ArrayList<Workout> workouts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Workout workout = new Workout();
                workout.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                workout.date = cursor.getString(cursor.getColumnIndex("date"));

                workouts.add(workout);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return workouts;
    }

    public ArrayList<Exercise> getAllExercises() {
        SQLiteDatabase db = this.getReadableDatabase();

        String getAllQuery = "SELECT * FROM " + EXERCISE_TABLE + ";";
        Cursor cursor = db.rawQuery(getAllQuery, null);

        ArrayList<Exercise> exercises = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Exercise exercise = new Exercise();
                exercise.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                exercise.name = cursor.getString(cursor.getColumnIndex("name"));
                exercise.weight = Integer.parseInt(cursor.getString(cursor.getColumnIndex("weight")));
                exercise.sets= Integer.parseInt(cursor.getString(cursor.getColumnIndex("sets")));

                exercises.add(exercise);

                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return exercises;
    }

    public ArrayList<Exercise> getAllExercisesForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String getAllQuery = "SELECT * FROM " + EXERCISE_TABLE + " WHERE date='" + date + "';";
        Cursor cursor = db.rawQuery(getAllQuery, null);

        ArrayList<Exercise> exercises = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Exercise exercise = new Exercise();
                exercise.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                exercise.name = cursor.getString(cursor.getColumnIndex("name"));
                exercise.weight = Integer.parseInt(cursor.getString(cursor.getColumnIndex("weight")));
                exercise.sets= Integer.parseInt(cursor.getString(cursor.getColumnIndex("sets")));

                exercises.add(exercise);

                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return exercises;
    }
}
