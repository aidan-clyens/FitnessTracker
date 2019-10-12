package com.aidanc.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

public class WorkoutTableHandler {
    private final static String TABLE_NAME = "workouts";

    private DatabaseHandler databaseHandler;
    private ExerciseTableHandler exerciseTableHandler;

    public WorkoutTableHandler(Context context) {
        databaseHandler = new DatabaseHandler(context, MainActivity.DATABASE_NAME);
        exerciseTableHandler = new ExerciseTableHandler(context);
    }

    public int addWorkout(String date) {
        ContentValues values = new ContentValues();
        values.put("date", date);

        return databaseHandler.insert(TABLE_NAME, values);
    }

    public void deleteWorkout(Workout workout) {
        databaseHandler.delete(TABLE_NAME, String.format("id=%d", workout.id));
        exerciseTableHandler.deleteExercisesFromWorkout(workout.id);
    }

    public Workout getWorkout(String date) {
        ArrayList<ArrayList<String>> entries = databaseHandler.getEntries("SELECT * FROM " + TABLE_NAME + " WHERE date='" + date + "' LIMIT 1");

        if (entries.size() > 0) {
            Workout workout = new Workout();
            workout.id = Integer.parseInt(entries.get(0).get(0));
            workout.date = entries.get(0).get(1);

            return workout;
        }

        return null;
    }

    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<ArrayList<String>> entries = databaseHandler.getEntries("SELECT * FROM " + TABLE_NAME);

        ArrayList<Workout> workouts = new ArrayList<>();

        for (ArrayList<String> row : entries) {
            Workout workout = new Workout();
            workout.id = Integer.parseInt(row.get(0));
            workout.date = row.get(1);
            workouts.add(workout);
        }

        return workouts;
    }
}
