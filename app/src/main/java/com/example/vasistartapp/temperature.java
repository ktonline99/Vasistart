package com.example.vasistartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;

public class temperature extends AppCompatActivity {
    double temp;
    boolean front_fan;
    boolean back_fan;
    boolean ac;
    boolean fan_off;
    boolean fan_low;
    boolean fan_mid;
    boolean fan_high;
    boolean fan_auto;
    boolean fahrenheit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        temp = getApp().getTemperature();
        front_fan = getApp().getFrontFanState();
        back_fan = getApp().getBackFanState();
        ac = getApp().getACState();
        setFanSpeed();
        setFanIcons();
        //changeFanSpeed();
        setTemperature();
        if(temp < 40){
            fahrenheit = false;
        }
        if(!fahrenheit){
            TextView degrees = findViewById(R.id.degrees);
            degrees.setText("  C");
        }

//        Handler handler=new Handler();
//        handler.post(new Runnable(){
//            @Override
//            public void run() {
//
//                temp = getApp().getTemperature();
//                front_fan = getApp().getFrontFanState();
//                back_fan = getApp().getBackFanState();
//                ac = getApp().getACState();
//                setFanSpeed();
//                setFanIcons();
//                //changeFanSpeed();
//                setTemperature();
//                if(temp < 40){
//                    fahrenheit = false;
//                }
//                if(!fahrenheit){
//                    TextView degrees = findViewById(R.id.degrees);
//                    degrees.setText("  C");
//                }
//
//                getApp().updateState();
//                handler.postDelayed(this,5000); // set time here to refresh textView
//            }
//        });
    }

    public GlobalClass getApp() {
        return ((GlobalClass) getApplication());
    }

    public void decreaseTemp(android.view.View v){
        if((temp > 60 && fahrenheit) || (temp > 15 && !fahrenheit)){
            temp -= 1;
            //getApp().setNewTemperature(temp);
            setTemperature();
        }
    }

    public void increaseTemp(android.view.View v){

        if((temp < 90 && fahrenheit) || (temp < 32 && !fahrenheit)){
            temp += 1;
            //getApp().setNewTemperature(temp);
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
        getApp().setACState(ac);
        setFanIcons();
    }

    public void switchFrontFanState(android.view.View v){
        if(front_fan == false){
            front_fan = true;
        } else {
            front_fan = false;
        }
        getApp().setFrontFanState(front_fan);
        setFanIcons();
    }

    public void switchBackFanState(android.view.View v){
        if(back_fan == false){
            back_fan = true;
        } else {
            back_fan = false;
        }
        getApp().setBackFanState(back_fan);
        setFanIcons();
    }

    // sets the TextView value of temperature
    public void setTemperature(){
        getApp().setNewTemperature(temp);
        TextView t = (TextView) findViewById(R.id.current_temperature);
        t.setText("" + (int) temp);

    }

    public void setFanIcons(){
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
            b_front.setColorFilter(Color.rgb(255,77,00));
        }
        ImageButton b_back = (ImageButton) findViewById(R.id.Back_fan_button);
        if(back_fan == false){
            b_back.setColorFilter(Color.BLACK);
        } else {
            b_back.setColorFilter(Color.rgb(255,77,00));
        }
    }

    public void setFanSpeed(){
        fan_off = false;
        fan_low = false;
        fan_mid = false;
        fan_high = false;
        fan_auto = false;

        String speed = getApp().getFanSpeed();

        if(speed.equals("LOW")){
            fan_low = true;
        } else if (speed.equals("HIGH")){
            fan_high = true;
        } else if (speed.equals("MED")){
            fan_mid = true;
        } else if (speed.equals("AUTO")){
            fan_auto = true;
        } else {
            fan_off = true;
        }

        changeFanSpeed();
    }

    public void fanOff(android.view.View v){
        if(fan_off == false){
            fan_off = true;
            fan_low = false;
            fan_mid = false;
            fan_high = false;
            fan_auto = false;
        }
        getApp().setFanSpeed("OFF");
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
        getApp().setFanSpeed("LOW");
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
        getApp().setFanSpeed("MED");
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
        getApp().setFanSpeed("HIGH");
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
        getApp().setFanSpeed("AUTO");
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