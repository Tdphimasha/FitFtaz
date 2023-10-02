package com.himasha.fitfatz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class Workout_gallery extends AppCompatActivity {
    private CardView cardView;
    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_gallery);

        // connect videos to cardViews
        cardView = (CardView) findViewById(R.id.basic1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playVideo(R.raw.video1);
            }
        });
        cardView = (CardView) findViewById(R.id.basic2);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playVideo(R.raw.video2);
            }
        });

        cardView = (CardView) findViewById(R.id.basic3);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playVideo(R.raw.video3);
            }
        });

        cardView = (CardView) findViewById(R.id.basic4);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playVideo(R.raw.video4);
            }
        });

        cardView = (CardView) findViewById(R.id.basic5);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playVideo(R.raw.video5);
            }
        });

        cardView = (CardView) findViewById(R.id.basic6);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playVideo(R.raw.video6);
            }
        });


    }

    @SuppressLint("MissingInflatedId")
    private void playVideo(int videoId) {
        setContentView(R.layout.activity_basic1);
        videoView = findViewById(R.id.videoView1);

        Uri uri = Uri.parse("android.resource://"+ getPackageName()+ "/" + videoId);
        videoView.setVideoURI(uri);
        videoView.start();

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish(); // to close the current activity
    }

}