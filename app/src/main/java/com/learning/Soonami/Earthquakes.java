package com.learning.Soonami;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquakes {
    private String date;
    private String time;
    private String distance;
    private String place;
    private String url;
    private double magnitude;

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getUrl() {
        return url;
    }

    public Earthquakes(long date, String place, double magnitude, String url) {
        Date dateObject = new Date(date);
        this.date = convertMillisecondsToDate(dateObject);
        this.time = convertMillisecondsToTime(dateObject);
        String[] strings = place.split(",");
        if(strings.length == 2)
        {
            this.distance= strings[0];
            this.place = strings[1];
        }
        else {
            this.place = "";
            this.place = strings[strings.length - 1];
        }
        this.magnitude = magnitude;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public String getDistance() {
        return distance;
    }

    private String convertMillisecondsToDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD, YYYY");
        return simpleDateFormat.format(date);
    }

    private String convertMillisecondsToTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        return simpleDateFormat.format(date);
    }

}
