package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner temp_spinner = (Spinner) findViewById(R.id.temperature_spinner);
        Spinner unit_spinner = (Spinner) findViewById(R.id.units_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.temp_arrays_F, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temp_spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> unit_adapter = ArrayAdapter.createFromResource(this,
                R.array.unit_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner.setAdapter(unit_adapter);

        //TODO actually change the temperature

        Switch moveNotifSwitch = (Switch) findViewById(R.id.moveNotifSwitch);
        Switch lockNotifSwitch = (Switch) findViewById(R.id.lockNotifSwitch);

        //TODO actually change the notifications

    }
}