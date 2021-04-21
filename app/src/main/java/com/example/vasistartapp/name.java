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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Button name = (Button) findViewById(R.id.name);
        EditText name_text = (EditText) findViewById(R.id.name_text);

        name.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){

                Intent intent = new Intent(v.getContext(), myvehicle.class);
                v.getContext().startActivity(intent);
            }
        });
    }


}