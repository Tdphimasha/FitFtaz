package com.himasha.fitfatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView gpsCard;
    private CardView workoutCard;
    private CardView goalCard;
    private CardView stepCounterCard;
    private CardView mealCard;
    private CardView bmiCalculatorCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize bottom navigation view and set the selected item
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_search:
                    // Start HeartRateActivity when search button is clicked
                    startActivity(new Intent(getApplicationContext(), HeartRateActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_calorie:
                    // Start CalorieCounter activity when calorie button is clicked
                    startActivity(new Intent(getApplicationContext(), CalorieCounter.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    // Start ProfileActivity when profile button is clicked
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_video:
                    // Start Workout_gallery activity when video button is clicked
                    startActivity(new Intent(getApplicationContext(), Workout_gallery.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        goalCard = findViewById(R.id.goal);
        workoutCard = findViewById(R.id.workout);
        gpsCard = findViewById(R.id.gps);
        stepCounterCard = findViewById(R.id.step_c);
        mealCard = findViewById(R.id.meal);
        bmiCalculatorCard = findViewById(R.id.bmi_cal);

        goalCard.setOnClickListener(this);
        workoutCard.setOnClickListener(this);
        gpsCard.setOnClickListener(this);
        stepCounterCard.setOnClickListener(this);
        mealCard.setOnClickListener(this);
        bmiCalculatorCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goal:
                // Start GoalActivity when goal card is clicked
                Intent goalIntent = new Intent(Dashboard.this, GoalActivity.class);
                startActivity(goalIntent);
                break;
            case R.id.workout:
                // Start WorkoutActivity when workout card is clicked
                Intent workoutIntent = new Intent(Dashboard.this, WorkoutActivity.class);
                startActivity(workoutIntent);
                break;
            case R.id.gps:
                // Start Gps_Tracking activity when GPS card is clicked
                Intent gpsIntent = new Intent(Dashboard.this, Gps_Tracking.class);
                startActivity(gpsIntent);
                break;
            case R.id.step_c:
                // Start Step_counter activity when Step Counter card is clicked
                Intent stepCounterIntent = new Intent(Dashboard.this, Step_counter.class);
                startActivity(stepCounterIntent);
                break;
            case R.id.meal:
                // Start Meal_tracker activity when Meal card is clicked
                Intent mealIntent = new Intent(Dashboard.this, Meal_tracker.class);
                startActivity(mealIntent);
                break;
            case R.id.bmi_cal:
                // Start BMICalculatorActivity when BMI Calculator card is clicked
                Intent bmiCalculatorIntent = new Intent(Dashboard.this, BMICalculatorActivity.class);
                startActivity(bmiCalculatorIntent);
                break;
        }
    }
}
