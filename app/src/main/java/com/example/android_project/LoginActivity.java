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

        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if ( !loginData.getString("userName", "").equals(""))
        {
            Intent I = new Intent(this, MainActivity.class);
            startActivity(I);
        }

        username = findViewById(R.id.userNameInput);
        password = findViewById(R.id.passwordInput);
        tv = findViewById(R.id.textView);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);

        if (getIntent().hasExtra("username")) {
            String username = getIntent().getStringExtra("username");
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

                if (checkBoxRememberMe.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", nn);
                    editor.putString("password", pp);
                    editor.putBoolean("rememberMe", true);
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

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}