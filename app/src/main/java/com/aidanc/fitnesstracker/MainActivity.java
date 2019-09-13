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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
    public final static int ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Display current date
        TextView dateView = (TextView) findViewById(R.id.date_view);
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        dateView.setText(currentDate);

        // Display list of saved exercises
        ListView exerciseList = (ListView) findViewById(R.id.exercise_list);
        // TODO: load saved exercises

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
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
