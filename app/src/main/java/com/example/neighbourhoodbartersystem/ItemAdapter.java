package com.example.neighbourhoodbartersystem;

import android.app.Notification;
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
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private static final String CHANNEL_ID = "alert_channel";

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
        itemButton.setOnClickListener(v -> showNotification(item));

        return convertView;
    }

    private void showNotification(String itemName) {
        Log.d("ItemAdapter", "showNotification called for item: " + itemName);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create notification channel for Android 8.0+ (API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alerts",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.settings) // Ensure you have this drawable
                .setContentTitle("New Alert")
                .setContentText("Item: " + itemName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify(999, builder.build()); // 1 is the notification ID
    }
}
