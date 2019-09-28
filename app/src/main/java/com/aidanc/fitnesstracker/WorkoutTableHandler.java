package com.aidanc.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

public class WorkoutTableHandler {
    private final static String TABLE_NAME = "workouts";

    private DatabaseHandler databaseHandler;

    public WorkoutTableHandler(Context context) {
        databaseHandler = new DatabaseHandler(context, MainActivity.DATABASE_NAME);
    }

    public int addWorkout(String date) {
        ContentValues values = new ContentValues();
        values.put("date", date);

        return databaseHandler.insert(TABLE_NAME, values);
    }

    public int getWorkout(String date) {
        ArrayList<ArrayList<String>> entries = databaseHandler.getEntries("SELECT * FROM " + TABLE_NAME + " WHERE date='" + date + "' LIMIT 1");

        if (entries.size() > 0) {
            return Integer.parseInt(entries.get(0).get(0));
        }
        else {
            return -1;
        }
    }

    public ArrayList<String> getAllWorkouts() {
        ArrayList<ArrayList<String>> entries = databaseHandler.getEntries("SELECT id FROM " + TABLE_NAME);

        ArrayList<String> dates = new ArrayList<>();

        for (ArrayList<String> row : entries) {
            dates.add(row.get(0));
        }

        return dates;
    }
}
