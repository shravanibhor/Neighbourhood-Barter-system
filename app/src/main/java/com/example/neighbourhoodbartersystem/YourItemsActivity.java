package com.example.neighbourhoodbartersystem;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class YourItemsActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView capturedImageView; // Declare globally
    private PopupWindow popupWindow;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youritems); // Set the layout resource
        Button showPopupButton = findViewById(R.id.button);
        showPopupButton.setOnClickListener(v -> showPopupWindow(v));
        ImageView filterIcon = findViewById(R.id.yourfilter);


        filterIcon.setOnClickListener(v -> showFilterPopup(v));

        // Find the ConstraintLayout in the activity layout
        ConstraintLayout constraintLayout = findViewById(R.id.youritemsm);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.exchange);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homepage) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.exchange) {
                // Handle exchange item click
                return true;
            } else if (item.getItemId() == R.id.settings) {
                startActivity(new Intent(this, ProfileActivity.class));
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
            Toast.makeText(this, "current page", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.exchange) {
            startActivity(new Intent(this, ExchangeActivity.class)); return true;
        } else if (item.getItemId() == R.id.ongoingm) {
            startActivity(new Intent(this, OngoingActivity.class)); return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    private void showPopupWindow(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.createnew, null);

        capturedImageView = popupView.findViewById(R.id.captured_image); // Store reference
        Button cameraButton = popupView.findViewById(R.id.camera_button);
        Button closeButton = popupView.findViewById(R.id.close_button);

        cameraButton.setOnClickListener(v -> checkPermissionsAndOpenCamera());
        closeButton.setOnClickListener(v -> popupWindow.dismiss());



        popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);closeButton.setOnClickListener(v -> popupWindow.dismiss());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap capturedBitmap = (Bitmap) extras.get("data");

                // Update ImageView inside the popup automatically
                if (capturedImageView != null && popupWindow != null && popupWindow.isShowing()) {
                    capturedImageView.setImageBitmap(capturedBitmap);
                }
            }
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

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> cameraApps = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (!cameraApps.isEmpty()) {  // Check if any camera apps exist
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app available!", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkPermissionsAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            openCamera();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();  // Open the camera if permission is granted
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
    }



}



