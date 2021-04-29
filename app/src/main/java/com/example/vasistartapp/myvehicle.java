package com.example.vasistartapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
//import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class myvehicle extends AppCompatActivity implements VehicleListener, VehicleListListener {

    ArrayList<String> vehiclesList;
    ArrayAdapter arrayAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvehicle);

        Vehicle[] vehicles = getGovt().vehicles;

        vehiclesList = new ArrayList<>();

        for(Vehicle v : vehicles) {
            vehiclesList.add(v.name);
        }

        arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1, vehiclesList);

        arrayAdapter.notifyDataSetChanged();

        ListView listView;

        listView=(ListView)findViewById(R.id.listv);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vehicleName = (String) listView.getItemAtPosition(position);
                getGovt().vehicleID = vehicleName;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button pair_another = (Button) findViewById(R.id.pair_another);
    }

    @Override
    public void onDifferentVehicles(Vehicle[] vehicles) {
        vehiclesList.clear();
        for(Vehicle v : vehicles) {
            vehiclesList.add(v.name);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    public void pairAnother (View v){
        Intent intent = new Intent(v.getContext(), start.class);
        v.getContext().startActivity(intent);}

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGovt().addListener(this);
        getGovt().addVehicleListListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getGovt().removeListener(this);
        getGovt().removeVehicleListListener(this);
    }

    public Government getGovt() {return ((GlobalClass) getApplication()).government;}


    @Override
    public void onVehicleChanged(Vehicle vehicle) {

    }

    @Override
    public void onNewVehicle(Vehicle vehicle) {
        getSupportActionBar().setTitle("VasiStart - " + vehicle.name);
    }


}

