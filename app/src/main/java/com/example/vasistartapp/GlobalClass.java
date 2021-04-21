package com.example.vasistartapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;


public class GlobalClass extends Application {
    private String car_id;
    private JSONObject state;
    private RequestQueue queue;
    public static final String CHANNEL_ID = "serviceChannel";

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public void updateState() {
        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        String url = "https://vovveti2.web.illinois.edu/vasistart/vehicle/test";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            state = response.getJSONObject("state");

                            Log.e("Successful Response: ", "" + response.getJSONObject("state").getDouble("temperature"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public void putState(JSONObject new_state) {
        Log.println(Log.INFO, "Put state called", "");
        String url = "https://vovveti2.web.illinois.edu/vasistart/vehicle/test/state";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, new_state,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        Log.e("Successful put", "");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
            }
        });

        queue.add(jsonRequest);
    }

    public boolean getEngineState()  {
        try {
            return state.getBoolean("engine_on");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void setEngineState(boolean engineState) {
        try {
            JSONObject temp_state = new JSONObject(state.toString());
            temp_state.put("engine_on", engineState);
            putState(temp_state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTemperature()  {
        try {
            return state.getInt("temperature");
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void setNewTemperature(int new_temperature) {
        try {
            JSONObject temp_state = new JSONObject(state.toString());
            temp_state.put("temperature", new_temperature);
            putState(temp_state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean getFrontFanState()  {
        try {
            return state.getBoolean("front_defrost_on");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void setFrontFanState(boolean new_state) {
        try {
            JSONObject temp_state = new JSONObject(state.toString());
            temp_state.put("front_defrost_on", new_state);
            putState(temp_state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean getBackFanState()  {
        try {
            return state.getBoolean("rear_defrost_on");
          } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }
  
    public LatLng getLocation() {
        try {
            double lat = state.getJSONArray("location").getDouble(0);
            double lon = state.getJSONArray("location").getDouble(1);
            return new LatLng(lat, lon);
        } catch (JSONException e) {
            e.printStackTrace();
            return new LatLng(40.10922071033943, -88.22722026154027);
        } catch (NullPointerException e) {
            return new LatLng(40.10922071033943, -88.22722026154027);
        }
    }
  
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        // Create an notification channel when staring the app
        createNotificationChannel();
        Intent intent = new Intent(this, NotificationService.class);
        startForegroundService(intent);
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

    public boolean getLockState() {
        try {
            return state.getBoolean("locked");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void setBackFanState(boolean new_state) {
        try {
            JSONObject temp_state = new JSONObject(state.toString());
            temp_state.put("rear_defrost_on", new_state);
            putState(temp_state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    public void setLockState(boolean lockState) {
        try {
            JSONObject temp_state = new JSONObject(state.toString());
            temp_state.put("locked", lockState);
            putState(temp_state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


