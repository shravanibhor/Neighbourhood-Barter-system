package com.example.neighbourhoodbartersystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends Activity {
    private SwitchCompat switchLocation;
    private SharedPreferences sharedPreferences;
    private TextView logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchLocation = findViewById(R.id.switch_location);
        logoutBtn = findViewById(R.id.logout_text);

        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE);

        // Load saved state
        boolean isLocationEnabled = sharedPreferences.getBoolean("LocationEnabled", false);
        switchLocation.setChecked(isLocationEnabled);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("LocationEnabled", isChecked);
                editor.apply();
            }
        });

        // Logout action
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear login status
                SharedPreferences loginPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginPrefs.edit();
                editor.clear();
                editor.apply();

                // Redirect to login
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

}
