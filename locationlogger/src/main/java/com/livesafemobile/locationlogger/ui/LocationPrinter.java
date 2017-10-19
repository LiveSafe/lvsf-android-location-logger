package com.livesafemobile.locationlogger.ui;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jshelly on 10/19/17.
 */

public class LocationPrinter {
    static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    static String printLocation(Location location){

        return "Provider=" + location.getProvider() + "\n" +
                "Time=" + sdf.format(new Date(location.getTime())) +"\n" +
                "Latitude=" + location.getLatitude() +"\n" +
                "Longitude=" + location.getLongitude() +"\n" +
                "HasAltitude=" + location.hasAltitude() +"\n" +
                "Altitude=" + location.getAltitude() +"\n" +
                "HasSpeed=" + location.hasSpeed() +"\n" +
                "Speed=" + location.getSpeed() +"\n" +
                "HasBearing=" + location.hasBearing() +"\n" +
                "Bearing=" + location.getBearing() +"\n" +
                "HasAccuracy=" + location.hasAccuracy() +"\n" +
                "Accuracy=" + location.getAccuracy() ;

    }
}
