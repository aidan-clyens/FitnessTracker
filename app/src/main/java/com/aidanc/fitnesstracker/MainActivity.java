package com.aidanc.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void refreshWorkouts() {
        // TODO: Get list of all workouts
    }
}
