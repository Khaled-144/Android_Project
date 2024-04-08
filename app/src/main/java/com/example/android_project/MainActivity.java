package com.example.android_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigation = findViewById(R.id.bottom_navigation); // Replace R.id.bottom_navigation with your actual BottomNavigationView id

        ContactFragment contactFragment = new ContactFragment();
        CatalogueFragment catalogueFragment = new CatalogueFragment();
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.contact) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, contactFragment).commit();
                    return true;
                } else if (itemId == R.id.catalogue) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, catalogueFragment).commit();
                    return true;
                } else if (itemId == R.id.shop) {
                    startActivity(new Intent(MainActivity.this, shopActivity1.class));
                    return true;
                } else if (itemId == R.id.logout) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    return true;
                } else if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, contactFragment).commit();
                    return true;
                }
                return true;
            }
        });

    }



    public void goToLoginActivity(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        // Go back to main activity
        startActivity(intent);
    }

    public void goToRegisterActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        // Go back to main activity
        startActivity(intent);
    }

    public void goToCatalogueActivity(View view){
        Intent intent = new Intent(this, CatalogueActivity.class);
        // Go back to main activity
        startActivity(intent);
    }
    public void goToshop1Activity(View view){
        Intent intent = new Intent(this, shopActivity1.class);
        // Go back to main activity
        startActivity(intent);
    }
    public void goToshop2Activity(View view){
        Intent intent = new Intent(this, shopActivity2.class);
        // Go back to main activity
        startActivity(intent);
    }
}