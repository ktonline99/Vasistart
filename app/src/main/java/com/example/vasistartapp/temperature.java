package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class temperature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        temp = getApp().getTemperature();
        setTemperature();
        front_fan = getApp().getFrontFanState();
        back_fan = getApp().getBackFanState();
        ac = false;
        setFans();
        changeFanSpeed();
    }

    int temp = 85;
    boolean front_fan = false;
    boolean back_fan = false;
    boolean ac = false;
    boolean fan_off = true;
    boolean fan_low = false;
    boolean fan_mid = false;
    boolean fan_high = false;
    boolean fan_auto = false;

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    public void decreaseTemp(android.view.View v){
        if(temp > 60){
            temp -= 1;
            setTemperature();
        }
    }

    public void increaseTemp(android.view.View v){

        if(temp < 90){
            temp += 1;
            setTemperature();
        }

    }

    public void returnToMain(android.view.View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchACState(android.view.View v){
        if(ac == false){
            ac = true;
        } else {
            ac = false;
        }

        setFans();
    }

    public void switchFrontFanState(android.view.View v){
        if(front_fan == false){
            front_fan = true;
        } else {
            front_fan = false;
        }
        getApp().setFrontFanState(front_fan);
        setFans();
    }

    public void switchBackFanState(android.view.View v){
        if(back_fan == false){
            back_fan = true;
        } else {
            back_fan = false;
        }
        getApp().setBackFanState(back_fan);
        setFans();
    }

    // sets the TextView value of temperature
    public void setTemperature(){
        getApp().setNewTemperature(temp);
        TextView t = (TextView) findViewById(R.id.current_temperature);
        t.setText("" + temp);
        TextView t_minus = (TextView) findViewById(R.id.one_minus_temperature);
        t_minus.setText("" + (temp-1));
        TextView t_plus = (TextView) findViewById(R.id.one_plus_temperature);
        t_plus.setText("" + (temp+1));

    }

    public void setFans(){
        ImageButton b_ac = (ImageButton) findViewById(R.id.AC_button);
        if(ac == false){
            b_ac.setColorFilter(Color.BLACK);
        } else {
            b_ac.setColorFilter(Color.rgb(105,161,250));
        }

        ImageButton b_front = (ImageButton) findViewById(R.id.Front_fan_button);
        if(front_fan == false){
            b_front.setColorFilter(Color.BLACK);
        } else {
            b_front.setColorFilter(Color.rgb(36,199,52));
        }
        ImageButton b_back = (ImageButton) findViewById(R.id.Back_fan_button);
        if(back_fan == false){
            b_back.setColorFilter(Color.BLACK);
        } else {
            b_back.setColorFilter(Color.rgb(36,199,52));
        }
    }

    public void fanOff(android.view.View v){
        if(fan_off == false){
            fan_off = true;
            fan_low = false;
            fan_mid = false;
            fan_high = false;
            fan_auto = false;
        }
        changeFanSpeed();
    }

    public void fanLow(android.view.View v){
        if(fan_low == false){
            fan_off = false;
            fan_low = true;
            fan_mid = false;
            fan_high = false;
            fan_auto = false;
        }
        changeFanSpeed();
    }

    public void fanMid(android.view.View v){
        if(fan_mid == false){
            fan_off = false;
            fan_low = false;
            fan_mid = true;
            fan_high = false;
            fan_auto = false;
        }
        changeFanSpeed();
    }

    public void fanHigh(android.view.View v){
        if(fan_high == false){
            fan_off = false;
            fan_low = false;
            fan_mid = false;
            fan_high = true;
            fan_auto = false;
        }
        changeFanSpeed();
    }

    public void fanAuto(android.view.View v){
        if(fan_auto == false){
            fan_off = false;
            fan_low = false;
            fan_mid = false;
            fan_high = false;
            fan_auto = true;
        }
        changeFanSpeed();
    }

    public void changeFanSpeed(){
        Button b_off = (Button) findViewById(R.id.fan_off_button);
        Button b_low = (Button) findViewById(R.id.fan_low_button);
        Button b_mid = (Button) findViewById(R.id.fan_mid_button);
        Button b_high = (Button) findViewById(R.id.fan_high_button);
        Button b_auto = (Button) findViewById(R.id.fan_auto_button);

        if(fan_off == false){
            b_off.setBackgroundColor(Color.argb(13, 67,19,212));
        } else {
            b_off.setBackgroundColor(Color.rgb(105,161,250));
        }
        if(fan_low == false){
            b_low.setBackgroundColor(Color.argb(13, 67,19,212));
        } else {
            b_low.setBackgroundColor(Color.rgb(105,161,250));
        }
        if(fan_mid == false){
            b_mid.setBackgroundColor(Color.argb(13, 67,19,212));
        } else {
            b_mid.setBackgroundColor(Color.rgb(105,161,250));
        }
        if(fan_high == false){
            b_high.setBackgroundColor(Color.argb(13, 67,19,212));
        } else {
            b_high.setBackgroundColor(Color.rgb(105,161,250));
        }
        if(fan_auto == false){
            b_auto.setBackgroundColor(Color.argb(13, 67,19,212));
        } else {
            b_auto.setBackgroundColor(Color.rgb(105,161,250));
        }
    }
}