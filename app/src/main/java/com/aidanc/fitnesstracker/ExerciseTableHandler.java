package com.aidanc.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

public class ExerciseTableHandler {
    private final static String TABLE_NAME = "exercises";

    private DatabaseHandler databaseHandler;

    public ExerciseTableHandler(Context context) {
        databaseHandler = new DatabaseHandler(context, MainActivity.DATABASE_NAME);
    }

    public int addExercise(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put("name", exercise.name);
        values.put("weight", exercise.weight);
        values.put("sets", exercise.sets);
        values.put("workout_id", exercise.workout_id);

        return databaseHandler.insert(TABLE_NAME, values);
    }

    public ArrayList<Exercise> getExercises(int workout_id) {
        ArrayList<ArrayList<String>> entries = databaseHandler.getEntries("SELECT * FROM " + TABLE_NAME + " WHERE workout_id=" + workout_id);

        ArrayList<Exercise> exercises = new ArrayList<>();

        for (ArrayList<String> row : entries) {
            Exercise exercise = new Exercise();
            exercise.id = Integer.parseInt(row.get(0));
            exercise.name = row.get(1);
            exercise.weight = Integer.parseInt(row.get(2));
            exercise.sets = Integer.parseInt(row.get(3));
            exercise.workout_id = workout_id;

            exercises.add(exercise);
        }

        return exercises;
    }
}
