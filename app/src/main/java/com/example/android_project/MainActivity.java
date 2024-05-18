package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, new HomeFragment())
                .commit();

        bottomNavigation = findViewById(R.id.bottom_navigation);

        BadgeDrawable badge = bottomNavigation.getOrCreateBadge(R.id.catalogue);
        badge.setVisible(true);
        badge.setNumber(3);

        ContactFragment contactFragment = new ContactFragment();
        CatalogueFragment catalogueFragment = new CatalogueFragment();
        HomeFragment homeFragment = new HomeFragment();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                    return true;
                }
                return true;
            }
        });
    }

    public void goToRegisterActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}