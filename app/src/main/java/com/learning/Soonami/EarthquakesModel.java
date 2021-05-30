package com.learning.Soonami;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EarthquakesModel {
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

    public EarthquakesModel(long timeInMilliseconds, String place, double magnitude, String url) {
        Date dateObject = new Date(timeInMilliseconds);
        this.date = convertMillisecondsToDate(dateObject);
        this.time = convertMillisecondsToTime(dateObject);
        String[] strings = place.split(",");
        if(strings.length == 2)
        {
            this.distance= strings[0].trim();
            this.place = strings[1].trim();
        }
        else {
            this.distance = "";
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD, YYYY", Locale.getDefault());
        simpleDateFormat.setTimeZone (TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

    private String convertMillisecondsToTime(Date date) {
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("h:mm a",Locale.getDefault());
        simpleDateFormat.setTimeZone (TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

}
