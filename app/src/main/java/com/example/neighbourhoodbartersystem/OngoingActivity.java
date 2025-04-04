package com.example.neighbourhoodbartersystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OngoingActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ongoing); // Set the layout resource

        // Find the ConstraintLayout in the activity layout
        ConstraintLayout constraintLayout = findViewById(R.id.ongoing);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ListView listView = findViewById(R.id.listView);
        List<String> itemList = new ArrayList<>();
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");

        ItemAdapter adapter = new ItemAdapter(this, itemList);
        listView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homepage) {
                // Handle homepage item click
                return true;
            } else if (item.getItemId() == R.id.exchange) {
                // Handle exchange item click
                return true;
            } else if (item.getItemId() == R.id.settings) {
                // Handle settings item click
                return true;
            } else {
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
            startActivity(new Intent(this, ExchangeActivity.class)); return true;
        } else if (item.getItemId() == R.id.ongoingm) {
            Toast.makeText(this, "current page", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}

