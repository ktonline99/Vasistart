package com.example.vasistartapp;

enum NotificationTo {
    GPS,
    LOCK
}

public class Notification {
    public String title;
    public String message;
    public NotificationTo to;
}
