package com.himasha.fitfatz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalorieCounter extends AppCompatActivity {
    private EditText editTextConsumed;
    private EditText editTextBurned;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        editTextConsumed = findViewById(R.id.editTextConsumed);
        editTextBurned = findViewById(R.id.editTextBurned);
        textViewResult = findViewById(R.id.textViewResult);

        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });
    }

    private void calculateCalories() {
        // Retrieve consumed and burned calories values from input fields
        int consumedCalories = Integer.parseInt(editTextConsumed.getText().toString());
        int burnedCalories = Integer.parseInt(editTextBurned.getText().toString());

        // Calculate total calories by subtracting burned calories from consumed calories
        int totalCalories = consumedCalories - burnedCalories;

        // Display the result
        textViewResult.setText("Total Calories: " + totalCalories);

    }
}
