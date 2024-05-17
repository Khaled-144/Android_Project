package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    TextView tv;
    String nn, pp;
    Connection conn;
    Statement stat;
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
        // new auto login
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if ( !loginData.getString("userName", "").equals(""))
        {
            Intent I = new Intent(this, MainActivity.class);
            startActivity(I);
        }

        // Initialize views
        username = findViewById(R.id.userNameInput);
        password = findViewById(R.id.passwordInput);
        tv = findViewById(R.id.textView);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        // Check if the activity is started with an intent that contains the username
        if (getIntent().hasExtra("username")) {
            // Retrieve the username from the intent
            String username = getIntent().getStringExtra("username");

            // Set the retrieved username to the EditText
            EditText usernameEditText = findViewById(R.id.userNameInput);
            usernameEditText.setText(username);
        }

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

        } catch (Exception e) {
            tv.setText("Error" + e.getMessage());
        }
    }

    public void vv(View view) {
        try {
            nn = username.getText().toString();
            pp = password.getText().toString();

            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "select * from usertable  where username= '" + nn.toString() + "' and userpass = '" + pp.toString() + "'  ";
            ResultSet rs = stat.executeQuery(query);

            if (rs.next()) {
                Intent I = new Intent(this, MainActivity.class);
                startActivity(I);

                // If "Remember me" checkbox is checked, save the username and password
                if (checkBoxRememberMe.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", nn); // Save username
                    editor.putString("password", pp); // Save password
                    editor.putBoolean("rememberMe", true); // Save rememberMe status
                    editor.apply();
                }
            } else {
                tv.setText("Invalid Credentials!");
            }
            conn.close();

        } catch (Exception e) {
            tv.setText("Error" + e.getMessage());
        }
    }

    public void getData(View view) {
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username.setText(loginData.getString("userName", ""));
        password.setText(loginData.getString("password", ""));
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        // Go back to main activity
        startActivity(intent);
    }
}
