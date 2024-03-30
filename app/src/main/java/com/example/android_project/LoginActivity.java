package com.example.android_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;


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

        //SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        //if ( !loginData.getString("userName", "").equals(""))
        //{
        //    Intent I = new Intent(LoginActivity.this, MainActivity.class);
        //    startActivity(I);
        //}

        username = (EditText) findViewById(R.id.userNameInput);
        password = (EditText) findViewById(R.id.passwordInput);

    }

    public void vv(View view) {
        try {
            nn = username.getText().toString();
            pp = password.getText().toString();

            conn= DriverManager.getConnection("jdbc:jtds:sqlserver://SQL5074.site4now.net/DB_A505E7_aiman","DB_A505E7_aiman_admin","pwxxxx");
            stat=conn.createStatement();
            String query = "select * from usertable  where username= '" + nn.toString() + "' and userpass = '" + pp.toString() + "'  ";
            ResultSet rs = stat.executeQuery(query);

            if (rs.next()) {
                Intent I = new Intent(this, MainActivity.class);
                startActivity(I);
            } else {
                tv.setText("Invalid Credentials!");
            }
            conn.close();

        } catch (Exception e) {
            tv.setText("Error"+e.getMessage());
        }
    }




    public void getData(View view){
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username.setText(loginData.getString("userName", "") );
        password.setText(loginData.getString("password", "") );
    }



}