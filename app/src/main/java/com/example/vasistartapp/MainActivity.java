package com.example.vasistartapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getApp().updateState();

        Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                // upadte textView here
                boolean engineOn = getApp().getEngineState();
                setEngineState(engineOn);
                getApp().updateState();
                handler.postDelayed(this,200); // set time here to refresh textView
            }
        });

        Switch lockSwitch = (Switch) findViewById(R.id.lockSwitch);
        if (lockSwitch != null) {
            lockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TextView lockText = (TextView) findViewById(R.id.lockText);
                    if (!isChecked) {
                        lockText.setText("Car is locked");
                        lockText.setTextColor(Color.BLACK);
                    } else {
                        lockText.setText("Car is unlocked!");
                        lockText.setTextColor(Color.RED);
                    }
                }
            });
        }

        updateTemperatureIcon();
    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pressEngine(View view) {
        getApp().setEngineState(!getApp().getEngineState());
        // TODO: Add delay between engine states
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setEngineState(boolean engineOn) {
        Button engineButton = (Button)findViewById(R.id.engineButton);
        if (!engineOn) {
            engineButton.setText("Turn on engine");
            engineButton.setBackgroundTintList(null);
            engineButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            engineButton.setText("Turn off engine");
            engineButton.setBackgroundTintList(null);
            engineButton.setBackgroundTintList(ColorStateList.valueOf(0xff13d443));
        }
        updateTemperatureIcon();
    }

    public void updateTemperatureIcon() {
        Button temperatureButton = (Button)findViewById(R.id.temperatureButton);
        Button engineButton = (Button)findViewById(R.id.engineButton);
        CharSequence currentState = engineButton.getText();
        if (currentState.equals("Turn off engine")) {
            temperatureButton.setEnabled(true);
        } else {
            temperatureButton.setEnabled(false);
        }
    }

    public void pressSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
  
    public void pressVehicles(View view) {
        Intent intent = new Intent(this, myvehicle.class);
        startActivity(intent);
    }

    public void pressTemperature(View view) {
        Intent intent = new Intent(this, temperature.class);
        startActivity(intent);
    }

    public void pressLocation(View view) {
        Intent intent = new Intent(this, GPSActivity.class);
        startActivity(intent);
    }

}