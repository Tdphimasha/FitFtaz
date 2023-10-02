package com.himasha.fitfatz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkoutActivity extends AppCompatActivity {

    private TextView exerciseNameTextView;
    private TextView timerTextView;
    private Button startButton;
    private Button stopButton;
    private VideoView exerciseVideoView;

    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeRemaining = 0;

    private List<String> exercises;
    private Random random;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        exerciseNameTextView = findViewById(R.id.exerciseNameTextView);
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        exerciseVideoView = findViewById(R.id.exerciseVideoView);

        exercises = new ArrayList<>();
        exercises.add("Push-ups");
        exercises.add("Sit-ups");
        exercises.add("Squats");
        exercises.add("Lunges");
        exercises.add("Plank");
        exercises.add("Chest");
        exercises.add("ABS");
        exercises.add("Quads");
        exercises.add("Shoulders");
        exercises.add("Biceps");
        exercises.add("Butt");

        random = new Random();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkout();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWorkout();
            }
        });
    }

    private void startWorkout() {
        if (!isRunning) {
            // Set exercise name
            String exerciseName = getRandomExerciseName();
            exerciseNameTextView.setText(exerciseName);

            // Update video source based on exercise name
            String videoPath = getVideoPathForExercise(exerciseName);
            exerciseVideoView.setVideoPath(videoPath);
            exerciseVideoView.start();

            // Set timer duration in milliseconds (10 seconds in this example)
            long timerDuration = 100000;

            countDownTimer = new CountDownTimer(timerDuration, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeRemaining = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    // Workout finished
                    stopWorkout();
                }
            };

            countDownTimer.start();

            isRunning = true;

            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void stopWorkout() {
        if (isRunning) {
            countDownTimer.cancel();
            timeRemaining = 0;
            updateTimer();

            isRunning = false;

            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    private void updateTimer() {
        int hours = (int) (timeRemaining / 3600000);
        int minutes = (int) ((timeRemaining / 60000) % 60);
        int seconds = (int) ((timeRemaining / 1000) % 60);

        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(time);
    }

    private String getRandomExerciseName() {
        // Generate a random index to select an exercise from the list
        int index = random.nextInt(exercises.size());
        return exercises.get(index);
    }

    private String getVideoPathForExercise(String exerciseName) {
        // Return the appropriate video path for the exercise
        if (exerciseName.equals("Push-ups")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.pushups;
        } else if (exerciseName.equals("Sit-ups")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.situps;
        } else if (exerciseName.equals("Squats")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.squats;
        } else if (exerciseName.equals("Lunges")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.video3;
        } else if (exerciseName.equals("Plank")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.plank;
        } else if (exerciseName.equals("Chest")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.chest;
        }else if (exerciseName.equals("ABS")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.abs;
        }else if (exerciseName.equals("Quads")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.quads;
        }else if (exerciseName.equals("Shoulders")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.shoulder;
        }else if (exerciseName.equals("Biceps")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.biceps;
        }else if (exerciseName.equals("Butt")) {
            return "android.resource://" + getPackageName() + "/" + R.raw.biceps;
        }
        else {
            // Default video path or handle unsupported exercise name
            return "";
        }
    }
}
