package com.example.vasistartapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
//import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class myvehicle extends AppCompatActivity {

    ArrayAdapter arrayAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvehicle);

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);

        ArrayList<String> list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this, R.layout.activity_myvehicle);

        String dataSet = data.getString("car", "");

        list.add(dataSet);
        arrayAdapter.notifyDataSetChanged();

        ListView listView=(ListView)findViewById(R.id.listv);
//        arrayAdapter = new ArrayAdapter<String>(this, R.id.listv, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        }

    public void pairAnother (View v){
        Intent intent = new Intent(v.getContext(), start.class);
        v.getContext().startActivity(intent);
    }
}

