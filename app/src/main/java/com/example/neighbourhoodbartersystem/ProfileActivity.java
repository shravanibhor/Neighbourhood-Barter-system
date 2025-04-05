package com.example.neighbourhoodbartersystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView settingsIcon = findViewById(R.id.settings_icon);
        Button editButton = findViewById(R.id.button3);
        EditText name = findViewById(R.id.profile_name);
        EditText email = findViewById(R.id.profile_email);
        EditText contact = findViewById(R.id.profile_contact);
        EditText userId = findViewById(R.id.profile_userid);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homepage) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.exchange) {
                // Handle exchange item click
                startActivity(new Intent(this, ExchangeActivity.class));
                return true;
            } else if (item.getItemId() == R.id.settings) {
                return true;
            } else {
                return false;
            }
        });
        editButton.setOnClickListener(v -> {
            boolean isEditable = name.isEnabled();

            // Toggle Edit Mode
            name.setEnabled(!isEditable);
            email.setEnabled(!isEditable);
            contact.setEnabled(!isEditable);
            userId.setEnabled(!isEditable);


            // Change Button Text
            if (!isEditable) {
                editButton.setText("Save");
            } else {
                editButton.setText("Edit");
                // Here, you can add code to save the updated details
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intent);
            }

        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
