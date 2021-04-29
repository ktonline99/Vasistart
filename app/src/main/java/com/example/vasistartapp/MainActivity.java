package com.example.vasistartapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements VehicleListener {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch lockSwitch = (Switch) findViewById(R.id.lockSwitch);
        lockSwitch.setClickable(false);
        lockSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    getGovt().getVehicle().state.locked ^= true;
                    setLockState(getGovt().getVehicle().state.locked);
                    getGovt().push();
                }
                return true;
            }
        });

        updateTemperatureIcon();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onNewVehicle(Vehicle vehicle) {
        setEngineState(vehicle.state.engine_on);
        setLockState(vehicle.state.locked);
        getSupportActionBar().setTitle("VasiStart - " + vehicle.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onVehicleChanged(Vehicle vehicle) {
        setEngineState(vehicle.state.engine_on);
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
    public void pressEngine(View view) {
        getGovt().getVehicle().state.engine_on ^= true;
        getGovt().push();
        // TODO: Add delay between engine states
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setEngineState(boolean engineOn) {
        Button engineButton = (Button)findViewById(R.id.engineButton);
        if (!engineOn) {
            engineButton.setText("Turn on engine");
            engineButton.setBackgroundTintList(null);
            engineButton.setBackgroundTintList(ColorStateList.valueOf(0xffd44313));
        } else {
            engineButton.setText("Turn off engine");
            engineButton.setBackgroundTintList(null);
            engineButton.setBackgroundTintList(ColorStateList.valueOf(0xff13d443));
        }
        updateTemperatureIcon();
    }

    public void setLockState(boolean lockState) {
        Switch lockSwitch = (Switch) findViewById(R.id.lockSwitch);
        lockSwitch.setChecked(!lockState);
        TextView lockText = (TextView) findViewById(R.id.lockText);
        if (lockState) {
            lockText.setText("Car is locked");
            lockText.setTextColor(Color.BLACK);
        } else {
            lockText.setText("Car is unlocked!");
            lockText.setTextColor(ColorStateList.valueOf(0xffd44313));
        }
    }

    public void updateTemperatureIcon() {
        Button temperatureButton = (Button)findViewById(R.id.temperatureButton);
        Button engineButton = (Button)findViewById(R.id.engineButton);
        CharSequence currentState = engineButton.getText();
        if (currentState.equals("Turn off engine")) {
            temperatureButton.setEnabled(true);
            temperatureButton.setTextColor(0xff000000);
        } else {
            temperatureButton.setEnabled(false);
            temperatureButton.setTextColor(0xff999999);
        }
    }

    public void pressSettings(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }
  
    public void pressVehicles(View view) {
        Intent intent = new Intent(this, myvehicle.class);
        startActivity(intent);
    }

    public void pressTemperature(View view) {
        Intent intent = new Intent(this, temperature.class);
        startActivity(intent);
    }

    public void pressLocation(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }

}