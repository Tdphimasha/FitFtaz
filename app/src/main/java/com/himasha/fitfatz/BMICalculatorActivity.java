package com.himasha.fitfatz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class BMICalculatorActivity extends AppCompatActivity {
    private EditText weightEditText;
    private EditText heightEditText;
    private TextView bmiResultTextView;
    private TextView bmiCategoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        // Initialize UI elements
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        bmiResultTextView = findViewById(R.id.bmiResultTextView);
        bmiCategoryTextView = findViewById(R.id.bmiCategoryTextView);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        // Retrieve weight and height values from input fields
        double weight = Double.parseDouble(weightEditText.getText().toString());
        double height = Double.parseDouble(heightEditText.getText().toString());

        // Calculate BMI using the BMICalculator class
        double bmi = BMICalculator.calculateBMI(weight, height);
        String bmiCategory = BMICalculator.getBMICategory(bmi);

        // Display BMI result and category
        bmiResultTextView.setText("BMI: " + bmi);
        bmiResultTextView.setVisibility(View.VISIBLE);

        bmiCategoryTextView.setText("BMI Category: " + bmiCategory);
        bmiCategoryTextView.setVisibility(View.VISIBLE);
    }
}
