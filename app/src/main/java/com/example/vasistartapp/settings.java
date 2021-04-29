package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

        Switch moveNotifSwitch = (Switch) findViewById(R.id.moveNotifSwitch);
        Switch lockNotifSwitch = (Switch) findViewById(R.id.lockNotifSwitch);

        //TODO actually change the notifications

    }

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String degrees = parent.getItemAtPosition(position).toString();
        double current_temp = getApp().getTemperature();

        if(degrees.equals("Celsius")){
            if(current_temp > 40){
                current_temp = ((current_temp-32) * 5)/9;
            }
        } else {
            if(current_temp < 40){
                current_temp = ((current_temp*9)/5) + 32;
            }
        }


        getApp().setNewTemperature(current_temp);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isFahrenheit(){
        double current_temp = getApp().getTemperature();

        if(current_temp > 40){
            return true;
        }

        return false;

    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }
}