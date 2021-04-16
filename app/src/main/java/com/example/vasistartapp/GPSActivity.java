package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);

        TextView locationText = (TextView)findViewById(R.id.location_text);
        LatLng union = new LatLng(40.10922071033943, -88.22722026154027);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(union.latitude, union.longitude, 1);
            if (addresses.size() != 1) {
                throw new IOException();
            }
            String address = addresses.get(0).getAddressLine(0);
            locationText.setText(getString(R.string.location_text_address,
                    "Vasista's Car", address));
        } catch (IOException e) {
            locationText.setText(getString(R.string.location_text_coord, union.latitude, union.longitude));
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng union = new LatLng(40.10922071033943, -88.22722026154027);
        googleMap.addMarker(new MarkerOptions()
            .position(union)
            .title("Car location"));
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(union, 18, 0, 0)));
    }
}