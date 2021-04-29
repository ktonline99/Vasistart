package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class name extends AppCompatActivity {

    private int vehicle_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Button name = (Button) findViewById(R.id.name);
        EditText name_text = (EditText) findViewById(R.id.name_text);
        SharedPreferences sharedPreferences = getSharedPreferences("MyVehicles", MODE_PRIVATE);

        name.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                vehicle_count++;
                String vc = String.valueOf(vehicle_count);
                SharedPreferences.Editor addVehicle = sharedPreferences.edit();
                addVehicle.putString("car", String.valueOf(name_text));
                addVehicle.commit();
                addVehicle.apply();

                Intent intent = new Intent(v.getContext(), myvehicle.class);
                v.getContext().startActivity(intent);
            }
        });
    }


}