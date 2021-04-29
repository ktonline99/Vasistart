package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener, VehicleListener {

    Switch moveNotifSwitch;
    Switch lockNotifSwitch;
    Switch lockAwayNotifSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner unit_spinner = (Spinner) findViewById(R.id.units_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temp_arrays_F, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(this,
                R.array.unit_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner.setAdapter(unit_adapter);

        unit_spinner.setSelected(false);
        if(isFahrenheit()){
            unit_spinner.setSelection(0, true);
        } else {
            unit_spinner.setSelection(1, true);
        }

        unit_spinner.setOnItemSelectedListener(this);

        //TODO actually change the temperature

        moveNotifSwitch = (Switch) findViewById(R.id.moveNotifSwitch);
        lockNotifSwitch = (Switch) findViewById(R.id.lockNotifSwitch);
        lockAwayNotifSwitch = (Switch) findViewById(R.id.lockAwayNotifSwitch);

        moveNotifSwitch.setClickable(false);
        moveNotifSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Vehicle v = getGovt().getVehicle();
                    v.state.notif_location ^= true;
                    setNotifSettingsStates(v.state.notif_location, v.state.notif_lock, v.state.notif_away_lock);
                    getGovt().push();
                }
                return true;
            }
        });

        lockNotifSwitch.setClickable(false);
        lockNotifSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Vehicle v = getGovt().getVehicle();
                    v.state.notif_lock ^= true;
                    setNotifSettingsStates(v.state.notif_location, v.state.notif_lock, v.state.notif_away_lock);
                    getGovt().push();
                }
                return true;
            }
        });

        lockAwayNotifSwitch.setClickable(false);
        lockAwayNotifSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Vehicle v = getGovt().getVehicle();
                    v.state.notif_away_lock ^= true;
                    setNotifSettingsStates(v.state.notif_location, v.state.notif_lock, v.state.notif_away_lock);
                    getGovt().push();
                }
                return true;
            }
        });

        //TODO actually change the notifications

    }

    public void setNotifSettingsStates(boolean move, boolean lock, boolean lockAway) {
        moveNotifSwitch.setChecked(move);
        lockNotifSwitch.setChecked(lock);
        lockAwayNotifSwitch.setChecked(lockAway);
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

    @Override
    public void onVehicleChanged(Vehicle vehicle) {

    }

    @Override
    public void onNewVehicle(Vehicle vehicle) {
        getSupportActionBar().setTitle("VasiStart - " + vehicle.name);
        moveNotifSwitch.setChecked(vehicle.state.notif_location);
        lockNotifSwitch.setChecked(vehicle.state.notif_lock);
        lockAwayNotifSwitch.setChecked(vehicle.state.notif_away_lock);
    }

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String degrees = parent.getItemAtPosition(position).toString();
        double current_temp = getGovt().getVehicle().state.temperature;

        if(degrees.equals("Celsius")){
            if(current_temp > 40){
                current_temp = ((current_temp-32) * 5)/9;
            }
        } else {
            if(current_temp < 40){
                current_temp = ((current_temp*9)/5) + 32;
            }
        }


        getGovt().getVehicle().state.temperature = current_temp;
        getGovt().push();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isFahrenheit(){
        double current_temp = getGovt().getVehicle().state.temperature;

        return current_temp > 40;

    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    public Government getGovt() {return ((GlobalClass) getApplication()).government;}

}