package com.aidanc.fitnesstracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
    public final static int ACTIVITY_REQUEST_CODE = 2;
    public final static String DATABASE_NAME = "fitness_data";

    WorkoutTableHandler workoutTableHandler;

    ListView workoutListView;
    ArrayList<Workout> workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workoutTableHandler = new WorkoutTableHandler(this);

        workoutListView = (ListView) findViewById(R.id.workout_list);
        workouts = refreshWorkouts();
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int workout_id = workouts.get(i).id;
                Log.d(TAG, String.format("workout_id = %d", workout_id));

                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                intent.putExtra("workout_id", workout_id);
                startActivityForResult(intent, WorkoutActivity.ACTIVITY_REQUEST_CODE);
            }
        });

        workoutListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, String.format("delete item %d", workouts.get(i).id));

                workoutTableHandler.deleteWorkout(workouts.get(i));
                workouts = refreshWorkouts();

                Toast.makeText(MainActivity.this, "Deleted workout", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        Button newWorkoutButton = (Button) findViewById(R.id.new_workout_button);
        newWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int workout_id = workoutTableHandler.getWorkout(getCurrentDate());
                Log.d(TAG, String.format("workout_id = %d", workout_id));

                if (workout_id == -1) {
                    workout_id = workoutTableHandler.addWorkout(getCurrentDate());
                }

                Intent i = new Intent(getApplicationContext(), WorkoutActivity.class);
                i.putExtra("workout_id", workout_id);
                startActivityForResult(i, WorkoutActivity.ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshWorkouts();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == WorkoutActivity.ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "Returned from WorkoutActivity");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public ArrayList<Workout> refreshWorkouts() {
        ArrayList<Workout> workoutListItems = workoutTableHandler.getAllWorkouts();
        ArrayList<String> workoutListDates = new ArrayList<>();

        for (Workout w : workoutListItems) {
            workoutListDates.add(w.date);
        }

        ArrayAdapter<String> workoutListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, workoutListDates);
        workoutListView.setAdapter(workoutListAdapter);

        return workoutListItems;
    }

    public String getCurrentDate() {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
    }
}
