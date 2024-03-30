package com.example.android_project;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {

    EditText username,password;
    TextView tv;
    String nn,pp;
    Connection conn;
    Statement stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username = (EditText) findViewById(R.id.txt1);
        password = (EditText) findViewById(R.id.txt2);
        tv=(TextView)findViewById(R.id.textView);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

        } catch (Exception e) {
            tv.setText("Error"+e.getMessage());
        }

    }

    public void ww(View view) {
        try {
            nn = username.getText().toString();
            pp = password.getText().toString();

            conn= DriverManager.getConnection("DataSource=SQL6031.site4now.net;Initial Catalog=db_aa717c_android;User Id=db_aa717c_android_admin;Password=W3P@g8pWivuWW2");
            stat=conn.createStatement();
            String query = "insert into  usertable (username,userpass) values ( '" + nn.toString() + "' ,'" + pp.toString() + "' ) ";
            int rs = stat.executeUpdate(query);
            tv.setText("Sucessfully Added");
            conn.close();

        } catch (Exception e) {
            tv.setText("Error"+e.getMessage());
        }
    }
}


