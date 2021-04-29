package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private Marker carLocation;
    private LatLng oldLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMap = null;
        carLocation = null;
        oldLocation = null;
        setContentView(R.layout.activity_g_p_s);
        oldLocation = getApp().getLocation();
        updateLocation(oldLocation);

        Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                LatLng newLocation = getApp().getLocation();
                float[] results = {0};
                Location.distanceBetween(oldLocation.latitude, oldLocation.longitude,
                        newLocation.latitude, newLocation.longitude, results);
                if (results[0] > 10) {
                    oldLocation = newLocation;
                    updateLocation(newLocation);
                }
                handler.postDelayed(this,5000);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        carLocation = myMap.addMarker(new MarkerOptions()
            .position(oldLocation)
            .title("Car location"));
        myMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(oldLocation, 18, 0, 0)));
    }

    public void updateLocation(LatLng location) {
        TextView locationText = (TextView)findViewById(R.id.location_text);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            if (addresses.size() != 1) {
                throw new IOException();
            }
            String address = addresses.get(0).getAddressLine(0);
            locationText.setText(getString(R.string.location_text_address,
                    "Vasista's Car", address));
        } catch (IOException e) {
            locationText.setText(getString(R.string.location_text_coord, location.latitude, location.longitude));
        }
        if (myMap != null) {
            carLocation.setPosition(location);
            myMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition(oldLocation, 18, 0, 0)));
        }
    }

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}