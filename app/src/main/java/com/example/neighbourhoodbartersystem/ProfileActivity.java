package com.example.neighbourhoodbartersystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
}
