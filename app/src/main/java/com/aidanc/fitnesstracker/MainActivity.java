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

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
    public final static int ACTIVITY_REQUEST_CODE = 0;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        // Display current date
        TextView dateView = (TextView) findViewById(R.id.date_view);
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        dateView.setText(currentDate);

        // Display list of saved exercises
        ListView exerciseList = (ListView) findViewById(R.id.exercise_list);
        // TODO: load saved exercises
        ArrayList<String> exerciseListItems = getExercises(currentDate);
        ArrayAdapter<String> exerciseListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseListItems);
        exerciseList.setAdapter(exerciseListAdapter);

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

    public ArrayList<String> getExercises(String date) {
        ArrayList<Exercise> exerciseListItems = databaseHelper.getAllExercisesForDate(date);

        ArrayList<String> temp = new ArrayList<>();

        for (Exercise e : exerciseListItems) {
            String s = String.format(e.name + "     weight: %d      sets: %d", e.weight, e.sets);
            temp.add(s);
        }

        return temp;
    }
}
