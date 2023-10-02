
package com.himasha.fitfatz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Step_counter extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView tvStepCount, tvStepGoal;
    private ProgressBar progressBar;
    private ImageView ivStepCounter;
    private Button btnReset;
    private boolean isCounterStarted = false;
    private int stepCount = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_step_counter);


        // Initialize views
        tvStepCount = findViewById(R.id.tv_step_count);
        tvStepGoal = findViewById(R.id.tv_step_goal);
        progressBar = findViewById(R.id.progress_bar);
        ivStepCounter = findViewById(R.id.iv_step_counter);
        btnReset = findViewById(R.id.btn_reset);

        // Set progress bar max to 10,000 steps
        progressBar.setMax(10000);

        // Initialize sensor manager and step sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        } else {
            tvStepCount.setText("Step Counter sensor not available");
        }

        // Set reset button click listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset step count and progress bar
                stepCount = 0;
                tvStepCount.setText("0");
                progressBar.setProgress(0);
                // Set step counter image to first level
                ivStepCounter.setImageResource(R.drawable.ic_step_counter_1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stepSensor != null) {
            sensorManager.unregisterListener(this, stepSensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (!isCounterStarted) {
                isCounterStarted = true;
                stepCount = (int) event.values[0];
            }
            int steps = (int) event.values[0] - stepCount;
            tvStepCount.setText(String.valueOf(steps));
            // Update progress bar based on number of steps taken
            progressBar.setProgress(steps);
            // Update step counter image based on number of steps taken
            if (steps < 100) {
                ivStepCounter.setImageResource(R.drawable.ic_step_counter_1);
            } else if (steps < 5000) {
                ivStepCounter.setImageResource(R.drawable.ic_step_counter_2);
            } else {
                ivStepCounter.setImageResource(R.drawable.ic_step_counter_3);
            }
            // Check if step goal has been reached
            if (steps >= 10000) {
                tvStepGoal.setText("Goal reached!");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
