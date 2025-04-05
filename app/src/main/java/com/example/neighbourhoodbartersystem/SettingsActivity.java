package com.example.neighbourhoodbartersystem;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends Activity {
    private SwitchCompat switchLocation;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchLocation = findViewById(R.id.switch_location);
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

    }

}
