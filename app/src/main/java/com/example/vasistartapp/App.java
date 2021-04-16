package com.example.vasistartapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class App extends Application {
    public static final String CHANNEL_ID = "serviceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        // Create an notification channel when staring the app
        createNotificationChannel();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(serviceChannel);
        }
    }
}
