package com.aidanc.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {
    public final static String TAG = "DatabaseHandler";

    private static final int DATABASE_VERSION = 1;
    private static final String WORKOUT_TABLE = "workouts";
    private static final String EXERCISE_TABLE = "exercises";

    public DatabaseHandler(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
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

    /**********     Public Methods    **********/
    public int insert(String table, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(table, null, values);

        db.close();

        return getLastId(table);
    }

    public ArrayList<ArrayList<String>> getEntries(String query) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> cols = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    cols.add(cursor.getString(i));
                }

                rows.add(cols);

                cursor.moveToNext();
            }
        }

        db.close();

        return rows;
    }

    public void clearTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        String clearQuery = "DELETE FROM " + table;
        Cursor cursor = db.rawQuery(clearQuery, null);

        cursor.close();
        db.close();
    }

    /**********     Private Methods    **********/
    private int getLastId(String table) {
        SQLiteDatabase db = this.getReadableDatabase();

        String getIdQuery = "SELECT id FROM " + table + " ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(getIdQuery, null);

        int id = -1;
        if (cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            }
        }

        Log.d(TAG, String.format("last id = %d", id));

        cursor.close();
        db.close();

        return id;
    }
}
