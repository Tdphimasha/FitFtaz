package com.himasha.fitfatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView firstNameTextView;
    private TextView emailTextView;
    private TextView usernameTextView;
    private TextView passwordTextView;
    private Button editProfileButton;

    private String firstName;
    private String email;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        firstNameTextView = findViewById(R.id.firstNameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        usernameTextView = findViewById(R.id.usernameTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        editProfileButton = findViewById(R.id.editProfileButton);

        // Get the profile information from the intent extras
        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        // Set profile information to the text views
        firstNameTextView.setText(firstName);
        emailTextView.setText(email);
        usernameTextView.setText(username);
        passwordTextView.setText(password);

        // Set click listener for edit profile button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open EditProfileActivity to edit profile
                Intent editIntent = new Intent(ProfileActivity.this, EditProfile.class);
                editIntent.putExtra("firstName", firstName);
                editIntent.putExtra("email", email);
                editIntent.putExtra("username", username);
                editIntent.putExtra("password", password);
                startActivityForResult(editIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the result is from the EditProfileActivity
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Retrieve the updated profile information from the intent
            firstName = data.getStringExtra("firstName");
            email = data.getStringExtra("email");
            username = data.getStringExtra("username");
            password = data.getStringExtra("password");

            // Update the profile information in the text views
            firstNameTextView.setText(firstName);
            emailTextView.setText(email);
            usernameTextView.setText(username);
            passwordTextView.setText(password);

            finish();
        }
    }
}
