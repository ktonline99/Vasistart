package com.example.vasistartapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class myvehicle extends AppCompatActivity {

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvehicle);

        getApp().updateVehicles();
        ArrayList<String> list = getApp().getVehicles();
        Log.e("something", (list == null)+" ");

        Button pair_another = (Button) findViewById(R.id.pair_another);

        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1, list);
        ListView listView;

        arrayAdapter.notifyDataSetChanged();

        listView=(ListView)findViewById(R.id.listv);
        listView.setAdapter(arrayAdapter);
        }

    public void pairAnother (View v){
        Intent intent = new Intent(v.getContext(), start.class);
        v.getContext().startActivity(intent);}
}

