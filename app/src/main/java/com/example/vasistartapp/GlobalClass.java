package com.example.vasistartapp;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GlobalClass extends Application {
    private String car_id;
    private JSONObject state;
    private RequestQueue queue;
    private ArrayList<String> vehicles;

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

    public void updateVehicles() {
        if (vehicles == null) {
            vehicles = new ArrayList<String>();
        }
        String vehicle_url = "https://vovveti2.web.illinois.edu/vasistart/vehicles";

        JsonArrayRequest jsonVehicleList = new JsonArrayRequest(Request.Method.GET, vehicle_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.
                        try {
                            vehicles.clear();
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject jobj = response.getJSONObject(i);
                                vehicles.add(jobj.getString("name"));
                            }
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
        queue.add(jsonVehicleList);
    }

    public void addVehicles(String name) {
        if (vehicles == null) {
            vehicles = new ArrayList<String>();
        }
        String vehicle_url = "https://vovveti2.web.illinois.edu/vasistart/vehicle/";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("user_id", name);

        Log.i("postParam", postParam.toString());

//        JsonObjectRequest josnObjReq = new JsonObjectRequest(Request.Method.POST, vehicle_url,
//                new JSONObject(postParam), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    String id = response.getString("id");
//                    JsonObjectRequest josnObjReq2 = new JsonObjectRequest(Request.Method.PUT, vehicle_url+id,
//                            new JSONObject(postParam), new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response2) {
//                            try {
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("VOLLEY", error.toString());
//            }
//        });
//        queue.add(josnObjReq);
    }

    public ArrayList<String> getVehicles() { return vehicles; }
}
