package com.example.vasistartapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class temperature extends AppCompatActivity implements VehicleListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
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
        setFans(vehicle.state.ac_on, vehicle.state.front_defrost_on, vehicle.state.rear_defrost_on);
        setFanSpeed(vehicle.state.ac_fan_speed);
        setTemperature((int) vehicle.state.temperature);
        getSupportActionBar().setTitle("VasiStart - " + vehicle.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onVehicleChanged(Vehicle vehicle) {
    }

    public void decreaseTemp(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.temperature -= 1;
        setTemperature((int) state.temperature);
        getGovt().push();
    }

    public void increaseTemp(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.temperature += 1;
        setTemperature((int) state.temperature);
        getGovt().push();
    }

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchACState(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.ac_on ^= true;
        setFans(state.ac_on, state.front_defrost_on, state.rear_defrost_on);
        getGovt().push();
    }

    public void switchFrontFanState(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.front_defrost_on ^= true;
        setFans(state.ac_on, state.front_defrost_on, state.rear_defrost_on);
        getGovt().push();
    }

    public void switchBackFanState(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.rear_defrost_on ^= true;
        setFans(state.ac_on, state.front_defrost_on, state.rear_defrost_on);
        getGovt().push();
    }

    // sets the TextView value of temperature
    public void setTemperature(int temp){
        TextView t = (TextView) findViewById(R.id.current_temperature);
        t.setText("" + temp);

        TextView degrees  = findViewById(R.id.degrees);
        degrees.setText(temp < 40 ? " C" : " F");
    }

    public void setFans(boolean ac, boolean front_fan, boolean back_fan){
        ImageButton b_ac = (ImageButton) findViewById(R.id.AC_button);
        if(!ac){
            b_ac.setColorFilter(Color.BLACK);
        } else {
            b_ac.setColorFilter(Color.rgb(105,161,250));
        }

        ImageButton b_front = (ImageButton) findViewById(R.id.Front_fan_button);
        if(!front_fan){
            b_front.setColorFilter(Color.BLACK);
        } else {
            b_front.setColorFilter(Color.rgb(255,77,0));
        }
        ImageButton b_back = (ImageButton) findViewById(R.id.Back_fan_button);
        if(!back_fan){
            b_back.setColorFilter(Color.BLACK);
        } else {
            b_back.setColorFilter(Color.rgb(255,77,0));
        }
    }

    public void fanOff(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.ac_fan_speed = FanSpeed.OFF;
        setFanSpeed(state.ac_fan_speed);
        getGovt().push();
    }

    public void fanLow(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.ac_fan_speed = FanSpeed.LOW;
        setFanSpeed(state.ac_fan_speed);
        getGovt().push();
    }

    public void fanMid(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.ac_fan_speed = FanSpeed.MED;
        setFanSpeed(state.ac_fan_speed);
        getGovt().push();
    }

    public void fanHigh(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.ac_fan_speed = FanSpeed.HIGH;
        setFanSpeed(state.ac_fan_speed);
        getGovt().push();
    }

    public void fanAuto(android.view.View v){
        State state = getGovt().getVehicle().state;
        state.ac_fan_speed = FanSpeed.AUTO;
        setFanSpeed(state.ac_fan_speed);
        getGovt().push();
    }

    public void setFanSpeed(FanSpeed fanSpeed){
        Button b_off = (Button) findViewById(R.id.fan_off_button);
        Button b_low = (Button) findViewById(R.id.fan_low_button);
        Button b_mid = (Button) findViewById(R.id.fan_mid_button);
        Button b_high = (Button) findViewById(R.id.fan_high_button);
        Button b_auto = (Button) findViewById(R.id.fan_auto_button);

        int colorSelected = Color.rgb(105,161,250);
        int colorDeselected = Color.argb(13,67,19, 212);

        b_off.setBackgroundColor(fanSpeed == FanSpeed.OFF ? colorSelected : colorDeselected);
        b_low.setBackgroundColor(fanSpeed == FanSpeed.LOW ? colorSelected : colorDeselected);
        b_mid.setBackgroundColor(fanSpeed == FanSpeed.MED ? colorSelected : colorDeselected);
        b_high.setBackgroundColor(fanSpeed == FanSpeed.HIGH ? colorSelected : colorDeselected);
        b_auto.setBackgroundColor(fanSpeed == FanSpeed.AUTO ? colorSelected : colorDeselected);
    }
}