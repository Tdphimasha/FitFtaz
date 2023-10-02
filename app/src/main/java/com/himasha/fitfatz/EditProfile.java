package com.himasha.fitfatz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText emailEditText;
    private EditText userNameEditText;
    private EditText passwordNameEditText;
    private Button saveProfileButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views
        firstNameEditText = findViewById(R.id.firstNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordNameEditText = findViewById(R.id.passwordNameEditText);
        saveProfileButton = findViewById(R.id.saveProfileButton);

        // Retrieve the current profile information from the intent
        String firstName = getIntent().getStringExtra("firstName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        // Set the current profile information to the edit text fields
        firstNameEditText.setText(firstName);
        emailEditText.setText(email);
        emailEditText.setText(username);
        passwordNameEditText.setText(password);

        // Set click listener for save profile button
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated profile information from the edit text fields
                String updatedFirstName = firstNameEditText.getText().toString();
                String updatedEmail = emailEditText.getText().toString();
                String updatedUsername = userNameEditText.getText().toString();
                String updatedPassword = passwordNameEditText.getText().toString();

                // Pass the updated profile information back to the ProfileActivity
                Intent intent = new Intent();
                intent.putExtra("firstName", updatedFirstName);
                intent.putExtra("email", updatedEmail);
                intent.putExtra("username", updatedUsername);
                intent.putExtra("password", updatedPassword);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
