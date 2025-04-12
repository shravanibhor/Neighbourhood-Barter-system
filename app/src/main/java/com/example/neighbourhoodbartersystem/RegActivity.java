package com.example.neighbourhoodbartersystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etPhone;
    Button btnRegister;
    String profilePic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                final String phone = etPhone.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(RegActivity.this, "All fields except profile pic are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegActivity.this, "Invalid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Background thread to send data to backend
                new Thread(() -> {
                    try {
                        URL url = new URL("http://192.168.1.5:5000/api/register"); // Enter your IP
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);

                        // Create JSON payload
                        JSONObject jsonInput = new JSONObject();
                        jsonInput.put("name", name);
                        jsonInput.put("email", email);
                        jsonInput.put("password", password);
                        jsonInput.put("phoneNumber", phone);
                        jsonInput.put("profilePicture", profilePic);
                        Log.d("JSON Input", jsonInput.toString());

                        // Send JSON to backend
                        OutputStream os = conn.getOutputStream();
                        os.write(jsonInput.toString().getBytes("utf-8"));
                        os.flush();
                        os.close();

                        // Handle Response
                        int responseCode = conn.getResponseCode();
                        Log.d("API_RESPONSE_CODE", "Response Code: " + responseCode);

                        if (responseCode == 201) {
                            runOnUiThread(() -> {
                                Toast.makeText(RegActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegActivity.this, LoginActivity.class));
                                finish();
                            });
                        } else {

                            runOnUiThread(() ->
                                    Toast.makeText(RegActivity.this, "Registration failed", Toast.LENGTH_SHORT).show()
                            );
                        }

                    } catch (Exception e) {
                        Log.e("RegisterError", "Registration failed: " + e.getMessage(), e);
                        runOnUiThread(() ->
                                Toast.makeText(RegActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                        );
                    }
                }).start();

                // Go back to LoginActivity
//                Intent intent = new Intent(RegActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish(); // Optional: prevent user from returning with back button
            }
        });

    }
}
