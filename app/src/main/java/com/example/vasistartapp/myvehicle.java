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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class myvehicle extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvehicle);

        Button pair_another = (Button) findViewById(R.id.pair_another);
        Button set_default = (Button) findViewById(R.id.set_default);
        ArrayList<String> list = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_myvehicle);
        ListView listView;

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(this);
        String dataSet = data.getString("car", "");

        list.add(dataSet);
        arrayAdapter.notifyDataSetChanged();

        listView=(ListView)findViewById(R.id.listv);
//        arrayAdapter = new ArrayAdapter<String>(this, R.id.listv, list);
        listView.setAdapter(arrayAdapter);


        set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Select a default vehicle");

                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                Set<String> vehicles = sharedPreferences.getStringSet("car", new HashSet<String>());
                CharSequence[] temp_vehicles = vehicles.toArray(new CharSequence[0]);
                builder.setItems(temp_vehicles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.add(0, (String) vehicles.toArray()[which]);
                    }
                });
                builder.show();
            }
        });
        }

    public void pairAnother (View v){
        Intent intent = new Intent(v.getContext(), start.class);
        v.getContext().startActivity(intent);}
}

