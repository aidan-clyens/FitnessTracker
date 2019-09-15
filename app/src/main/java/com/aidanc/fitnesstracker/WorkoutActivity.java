package com.aidanc.fitnesstracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WorkoutActivity extends AppCompatActivity {
    public final static String TAG = "WorkoutActivity";
    public final static int ACTIVITY_REQUEST_CODE = 0;

    DatabaseHelper databaseHelper;

    ListView exerciseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        databaseHelper = new DatabaseHelper(this);

        // Display current date
        String currentDate = getCurrentDate();
        TextView dateView = (TextView) findViewById(R.id.date_view);
        dateView.setText(currentDate);

        // Display list of saved exercises
        exerciseListView = (ListView) findViewById(R.id.exercise_list);
        refreshExercisesList(currentDate);

        // Button to add a new exercise to this workout
        Button addExerciseButton = (Button) findViewById(R.id.add_exercise_button);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewExerciseActivity.class);
                startActivityForResult(i, NewExerciseActivity.ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshExercisesList(getCurrentDate());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NewExerciseActivity.ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Exercise exercise = (Exercise) data.getSerializableExtra("result");
                    databaseHelper.addExercise(exercise);
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshExercisesList(String date) {
        // Display list of saved exercises
        ArrayList<String> exerciseListItems = getExercises(date);
        ArrayAdapter<String> exerciseListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseListItems);
        exerciseListView.setAdapter(exerciseListAdapter);
    }

    public ArrayList<String> getExercises(String date) {
        ArrayList<Exercise> exerciseListItems = databaseHelper.getAllExercisesForDate(date);

        ArrayList<String> temp = new ArrayList<>();

        for (Exercise e : exerciseListItems) {
            String s = String.format(e.name + "     weight: %d      sets: %d", e.weight, e.sets);
            temp.add(s);
        }

        return temp;
    }

    public String getCurrentDate() {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
    }
}
