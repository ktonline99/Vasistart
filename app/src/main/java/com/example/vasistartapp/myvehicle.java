package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class myvehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvehicle);

        Button pair_another = (Button) findViewById(R.id.pair_another);
        Button set_default = (Button) findViewById(R.id.set_default);

        pair_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), start.class);
                v.getContext().startActivity(intent);}
        });

        set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(v.getContext(), default_vehicle.class);
                v.getContext().startActivity(intent);}
        });
    }
}