package com.aidanc.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

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
                Exercise exercise = new Exercise();
                exercise.name = exerciseNameInput.getText().toString();
                exercise.weight = Integer.parseInt(weightInput.getText().toString());
                exercise.sets = Integer.parseInt(setsInput.getText().toString());
            }
        });
    }
}
