package com.example.vasistartapp;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.graphics.Path;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

public class Government {

    private Set<VehicleListener> listeners = new HashSet<VehicleListener>();
    private Handler handler;
    private RequestQueue requestQueue;
    private String serverUrl = "https://vovveti2.web.illinois.edu/vasistart/";
//    private String serverUrl = "http://10.0.2.2:8000/";
    public String vehicleID = "test";

    private String oldData;
    private Vehicle vehicle = null;

    private ObjectMapper objectMapper = new ObjectMapper();

    JsonObjectRequest getRequest;

    public Government(RequestQueue requestQueue2) {
        requestQueue = requestQueue2;
        handler = new Handler();

        // Request a string response from the provided URL.
        getRequest = new JsonObjectRequest(Request.Method.GET, serverUrl + "vehicle/" + vehicleID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        String newData = response.toString();
                        if (newData.equals(oldData)) return;
                        oldData = newData;

//                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            Vehicle newVehicle = objectMapper.readValue(newData, Vehicle.class);

                            if (vehicle == null || !vehicle.id.equals(newVehicle.id)){
                                for(VehicleListener g : listeners) {
                                    g.onNewVehicle(newVehicle);
                                }
                            }

                            for(VehicleListener g : listeners) {
                                g.onVehicleChanged(newVehicle);
                            }

                            vehicle = newVehicle;

                            String updatedData = objectMapper.writeValueAsString(newVehicle);
                            JSONObject j = new JSONObject(updatedData);

                            JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, serverUrl + "vehicle/" + vehicleID, j,
                                    response1 -> {
                                        // Display the first 500 characters of the response string.
                                        Log.e("Successful put", "");
                                    }, error -> {}
                            );

                            requestQueue.add(putRequest);

                        } catch (JsonProcessingException | JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, error -> { if(error != null && error.getMessage() != null) Log.e("Error: ", error.getMessage());});

        handler.post(new Runnable() {
            @Override
            public void run() {
                requestQueue.add(getRequest);
                handler.postDelayed(this, 500);
            }
        });

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle v){
        String updatedData = null;
        try {
            updatedData = objectMapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        JSONObject j = null;
        try {
            j = new JSONObject(updatedData);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        Log.d("Gov Set Vehicle", j.toString());

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, serverUrl + "vehicle/" + vehicleID, j,
            response1 -> {
                // Display the first 500 characters of the response string.
                Log.e("Successful put", "");

            }, error -> {
//                if (error != null && error.getMessage() != null)
//                Log.e("Error: ", error.getMessage());
            }
        );

        requestQueue.add(putRequest);
    }

    public void push() {
        setVehicle(vehicle);
    }

    public void addListener(VehicleListener listener) {
        listeners.add(listener);
        if(vehicle != null) listener.onNewVehicle(vehicle);
    }

    public void removeListener(VehicleListener listener) {
        listeners.remove(listener);
    }


}