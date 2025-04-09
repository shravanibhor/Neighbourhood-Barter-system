package com.example.neighbourhoodbartersystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TextView homeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        if (!prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeContent = findViewById(R.id.heading);

        // Bottom Navigation Setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();  // Store in a variable to avoid issues

                if (itemId == R.id.homepage) {
                    homeContent.setText("Welcome to Neighbourhood Barter System!");
                    return true;
                } else if (itemId == R.id.exchange) {
                    startActivity(new Intent(MainActivity.this, ExchangeActivity.class));
                    return true;
                } else if (itemId == R.id.settings) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Button to add a new product
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddNewProduct.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.chat) {
            homeContent.setText("Previous Chats[dm]");
            return true;
        } else if (id == R.id.exchange) {
            homeContent.setText("Exchange page\nList of products available:");
            return true;
        } else if (id == R.id.profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
