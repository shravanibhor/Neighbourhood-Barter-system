package com.example.neighbourhoodbartersystem;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import java.util.List;
import java.util.Random;

public class ItemAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private static final String CHANNEL_ID = "alert_channel";
    private int generatedOTP; // Store the generated OTP

    public ItemAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        TextView itemText = convertView.findViewById(R.id.item_text);
        Button itemButton = convertView.findViewById(R.id.item_button);

        String item = items.get(position);
        itemText.setText(item);

        // Set button click listener
        itemButton.setOnClickListener(v -> {
            generatedOTP = generateOTP();
            showNotification(item, generatedOTP); // Send OTP via notification
            showOTPDialog(); // Open OTP input dialog
        });

        return convertView;
    }

    private void showNotification(String itemName, int otp) {
        Log.d("ItemAdapter", "showNotification called for item: " + itemName);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification with the OTP
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.settings)
                .setContentTitle("OTP for Transaction")
                .setContentText("Your OTP: " + otp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(999, builder.build());
    }

    private void showOTPDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter OTP");

        // Create an EditText field for user input
        final EditText input = new EditText(context);
        builder.setView(input);

        // Set up buttons
        builder.setPositiveButton("Submit", (dialog, which) -> {
            int enteredOTP;
            try {
                enteredOTP = Integer.parseInt(input.getText().toString());
            } catch (NumberFormatException e) {
                enteredOTP = -1; // Invalid input
            }

            if (enteredOTP == generatedOTP) {
                Toast.makeText(context, "OTP Verified!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Incorrect OTP. Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private int generateOTP() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // Generates a 4-digit OTP
    }
}
