package com.aidanc.fitnesstracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
    public final static int ACTIVITY_REQUEST_CODE = 2;

    ListView workoutListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workoutListView = (ListView) findViewById(R.id.workout_list);
        refreshWorkouts();

        Button newWorkoutButton = (Button) findViewById(R.id.new_workout_button);
        newWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Go to new workout screen
                Intent i = new Intent(getApplicationContext(), WorkoutActivity.class);
                startActivityForResult(i, WorkoutActivity.ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == WorkoutActivity.ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO: Add new workout
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshWorkouts() {
        // TODO: Get list of all workouts
    }
}
