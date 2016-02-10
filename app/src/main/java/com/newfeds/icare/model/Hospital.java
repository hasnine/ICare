package com.newfeds.icare.model;

/**
 * Created by GT on 1/26/2016.
 */
public class Hospital {
    public String name;
    public String lat;
    public String lon;

    public Hospital() {
    }

    public Hospital(String name, String lat, String lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
