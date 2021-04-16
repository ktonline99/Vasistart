package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class temperature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int temp = 60;
    public void decreaseTemp(android.view.View v){
        TextView t = (TextView) v;
        if(temp > 60){
            t.setText(temp - 1);
        }
    }

    public void increaseTemp(android.view.View v){
        TextView t = (TextView) v;
        if(temp < 90){
            t.setText(temp + 1);
        }

    }
}