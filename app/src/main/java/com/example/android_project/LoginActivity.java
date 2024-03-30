package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
     EditText editTextUsername, editTextPassword;
     CheckBox checkBoxRememberMe;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Check if username and password are remembered
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isRemembered = preferences.getBoolean("isRemembered", false);
        if (isRemembered) {
            String savedUsername = preferences.getString("username", "");
            String savedPassword = preferences.getString("password", "");
            editTextUsername.setText(savedUsername);
            editTextPassword.setText(savedPassword);
            checkBoxRememberMe.setChecked(true);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validate username and password (add your own validation logic here)
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkBoxRememberMe.isChecked()) {
                        // Remember username and password
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isRemembered", true);
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();
                    } else {
                        // Clear saved username and password
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                    // Proceed with login
                    // For demonstration purposes, always redirect to MainActivity
                    redirectToMainActivity();
                }
            }
        });
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // (TODO: Remove this line, because we didn't study it) Prevents the user from returning to the LoginActivity using the back button
    }
}