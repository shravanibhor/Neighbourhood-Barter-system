package com.example.neighbourhoodbartersystem;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExchangeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange); // Set the layout resource

        // Find the ConstraintLayout in the activity layout
        ConstraintLayout constraintLayout = findViewById(R.id.exchange);

        // Find the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView filterIcon = findViewById(R.id.filter_iconex);



        filterIcon.setOnClickListener(v -> showFilterPopup(v));
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homepage) {
                return true;
            } else if (item.getItemId() == R.id.exchange) {
                // Handle exchange item click
                return true;
            } else if (item.getItemId() == R.id.settings) {
                // Handle settings item click
                return true;
            } else if (item.getItemId() == R.id.chat) {
                // Handle settings item click
                return true;
            }else {
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ex_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.youritemsm) {
            startActivity(new Intent(this, YourItemsActivity.class)); return true;
        } else if (item.getItemId() == R.id.exchangem) {
            Toast.makeText(this, "current page", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.ongoingm) {
            startActivity(new Intent(this, OngoingActivity.class)); return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void showFilterPopup(View view) {
        // Inflate the popup layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.filter_popup, null);

        // Create the PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // Get references to popup UI elements
        EditText priceInput = popupView.findViewById(R.id.price_input);
        Spinner categorySpinner = popupView.findViewById(R.id.category_spinner);
        Button applyButton = popupView.findViewById(R.id.apply_button);
        Button closeButton = popupView.findViewById(R.id.close_popup);

        // Set up Spinner with categories
        String[] categories = {"Select Category", "Electronics", "Clothing", "Books"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

        // Close button listener
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        // Apply button listener
        applyButton.setOnClickListener(v -> {
            String selectedCategory = categorySpinner.getSelectedItem().toString();
            String price = priceInput.getText().toString();
            Toast.makeText(this, "Filter Applied: " + selectedCategory + ", Max Price: " + price, Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });
    }


}

