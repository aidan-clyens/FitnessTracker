package com.aidanc.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewExerciseActivity extends AppCompatActivity {
    final static String TAG = "NewExerciseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        final EditText exerciseNameInput = (EditText) findViewById(R.id.exercise_input);
        final EditText weightInput = (EditText) findViewById(R.id.weight_input);
        final EditText setsInput = (EditText) findViewById(R.id.sets_input);

        Button addButton = (Button) findViewById(R.id.submit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: ensure that inputs are not empty
                submitExercise(exerciseNameInput.getText().toString(), Integer.parseInt(weightInput.getText().toString()), Integer.parseInt(setsInput.getText().toString()));
            }
        });
    }

    private void submitExercise(String name, int weight, int sets) {
        Exercise exercise = new Exercise();
        exercise.name = name;
        exercise.weight = weight;
        exercise.sets = sets;

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}