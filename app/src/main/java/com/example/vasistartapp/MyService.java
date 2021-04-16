package com.example.vasistartapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MyService extends Service implements Runnable{
    private NotificationManagerCompat notificationManager;
    // For local test only
    private String url = "http://10.183.78.242:10000/api/1/?format=json";
    private static final int NOTIFICATION_ID = 2;

    @Override
    public void run() {
//        Log.e("MYSERVICE", "run");
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                Log.e("TEST", "is running");
//                RequestQueue requestQueue = Volley.newRequestQueue(MyService.this);
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

    }

//    private void setNotificationMessage(CharSequence message) {
//
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MYSERVICE", "onCreate");
        new Thread(new Runnable(){
            @Override
            public void run() {
                Log.e("TEST", "is running");
                RequestQueue requestQueue = Volley.newRequestQueue(MyService.this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        response -> {
                            Log.e("Request Response", response.toString());
                            try {
                                if (response.get("isMoving").toString() == "false") {
                                    pushNotification();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Log.e("Error Message", error.toString()));
                requestQueue.add(objectRequest);
            }
        }).start();
    }

    public void pushNotification() {
//        String location = editTextLocation.getText().toString();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, LocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String title = "Your Car is Moving!";
        String message = "Click to check the vecicle location.";
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }
}
