package com.example.vasistartapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSActivity extends AppCompatActivity implements OnMapReadyCallback, VehicleListener {
    private GoogleMap myMap;
    private Marker carMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMap = null;
        setContentView(R.layout.activity_g_p_s);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGovt().addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getGovt().removeListener(this);
    }

    public Government getGovt() {return ((GlobalClass) getApplication()).government;}

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onNewVehicle(Vehicle vehicle) {
        getSupportActionBar().setTitle("VasiStart - " + vehicle.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onVehicleChanged(Vehicle vehicle) {
        updateLocation(new LatLng(vehicle.state.location.latitude, vehicle.state.location.longitude), vehicle.name);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        Vehicle vehicle = getGovt().getVehicle();
        State state = vehicle.state;
        LatLng loc = new LatLng(state.location.latitude, state.location.longitude);
        carMarker = myMap.addMarker(new MarkerOptions()
            .position(loc)
            .title("Car location"));
        myMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(loc, 18, 0, 0)));

        updateLocation(loc, vehicle.name);
    }

    public void updateLocation(LatLng location, String vehicleName) {
        TextView locationText = (TextView)findViewById(R.id.location_text);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses.size() != 1) {
                throw new IOException();
            }
            String address = addresses.get(0).getAddressLine(0);
            locationText.setText("Location History: " + vehicleName + " is currently at " + address);
        } catch (IOException e) {
            locationText.setText("Lat: " + location.latitude + ", Long: " + location.longitude);
        }
        if (myMap != null) {
            carMarker.setPosition(location);
            myMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition(location, 18, 0, 0)));
        }
    }

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}