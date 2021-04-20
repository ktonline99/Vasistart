package com.example.vasistartapp;

class Features{
    public boolean temperature;
    public boolean front_defrost;
    public boolean rear_defrost;
    public boolean lock;
    public boolean location;
    public boolean engine;
}

class Location{
    public double latitude;
    public double longitude;
}

enum FanSpeed{
    OFF,
    LOW,
    MED,
    HIGH,
    AUTO
}

enum VehicleType{
    CAR,
    MOTORCYCLE
}

class State{
    public double temperature;
    public boolean front_defrost_on;
    public boolean rear_defrost_on;
    public boolean ac_on;
    public FanSpeed ac_fan_speed;
    public boolean locked;
    public boolean engine_on;
    public boolean engine_on_desired;
    public Location location;
    public boolean notif_loc;
    public int notif_unlock_delay;
}

public class Vehicle{
    public String name;
    public String id;
    public VehicleType type;
    public Features features;
    public State state;
}
