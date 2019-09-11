package com.aidanc.fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateView = (TextView) findViewById(R.id.date_view);
        String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        dateView.setText(currentDate);

        ListView exerciseList = (ListView) findViewById(R.id.exercise_list);
        // TODO: load saved exercises

        Button addExerciseButton = (Button) findViewById(R.id.add_exercise_button);
        // TODO: switch to add exercise activity

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewExerciseActivity.class);
                startActivity(i);
            }
        });
    }
}
