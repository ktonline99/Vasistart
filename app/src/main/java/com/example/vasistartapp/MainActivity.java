package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    protected NotificationManagerCompat notificationManager;
//    private EditText editTextLocation;
//    private String url = "https://google.com";
    private String url = "http://10.183.78.242:10000/api/1/?format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        notificationManager = NotificationManagerCompat.from(this);
//
//        editTextLocation = findViewById(R.id.location_text_input);
//        test();
    }

//    protected void test(){
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                Log.e("TEST", "is running");
//                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
//                        url,
//                        null,
//                        response -> {
//                            Log.e("Request Response", response.toString());
//                            try {
//                                if (response.get("isMoving").toString() == "false") {
//                                    pushNotification();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        },
//                        error -> Log.e("Error Message", error.toString()));
//                requestQueue.add(objectRequest);
//            }
//        }).start();
//    }
//
//    public void pushNotification() {
////        String location = editTextLocation.getText().toString();
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent intent = new Intent(this, LocationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        String title = "Your Car is Moving!";
//        String message = "Click to check the vecicle location.";
//        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_android_black_24dp)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_ALARM)
//                .setContentIntent(pendingIntent)
//                .build();
//        notificationManager.notify(1, notification);
//    }
}