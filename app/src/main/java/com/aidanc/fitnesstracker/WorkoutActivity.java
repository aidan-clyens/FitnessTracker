package com.aidanc.fitnesstracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WorkoutActivity extends AppCompatActivity {
    public final static String TAG = "WorkoutActivity";
    public final static int ACTIVITY_REQUEST_CODE = 0;

    ExerciseTableHandler exerciseTableHandler;

    ListView exerciseListView;
    ArrayList<Exercise> exercises;

    int workout_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        exerciseTableHandler = new ExerciseTableHandler(this);

        workout_id = this.getIntent().getIntExtra("workout_id", -1);
        Log.d(TAG, String.format("workout_id = %d", workout_id));

        // Display current date
        String currentDate = getCurrentDate();
        TextView dateView = (TextView) findViewById(R.id.date_view);
        dateView.setText(currentDate);

        // Display list of saved exercises
        exerciseListView = (ListView) findViewById(R.id.exercise_list);
        exercises = refreshExercisesList();
        exerciseListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, String.format("delete item %d", exercises.get(i).id));

                exerciseTableHandler.deleteExercise(exercises.get(i));
                exercises = refreshExercisesList();

                Toast.makeText(WorkoutActivity.this, "Deleted exercise", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        // Button to add a new exercise to this workout
        Button addExerciseButton = (Button) findViewById(R.id.add_exercise_button);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewExerciseActivity.class);
                startActivityForResult(i, NewExerciseActivity.ACTIVITY_REQUEST_CODE);
            }
        });

        Button addWorkoutButton = (Button) findViewById(R.id.new_workout_button);
        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshExercisesList();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NewExerciseActivity.ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Exercise exercise = (Exercise) data.getSerializableExtra("result");
                    exercise.workout_id = workout_id;
                    exerciseTableHandler.addExercise(exercise);
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public ArrayList<Exercise> refreshExercisesList() {
        // Display list of saved exercises
        ArrayList<Exercise> exerciseListItems = exerciseTableHandler.getExercises(workout_id);

        ArrayList<String> exercisesListString = new ArrayList<>();

        for (Exercise e : exerciseListItems) {
            String s = String.format(e.name + "     weight: %d      sets: %d", e.weight, e.sets);
            exercisesListString.add(s);
        }

        ArrayAdapter<String> exerciseListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, exercisesListString);
        exerciseListView.setAdapter(exerciseListAdapter);

        return exerciseListItems;
    }

    public String getCurrentDate() {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
    }
}
