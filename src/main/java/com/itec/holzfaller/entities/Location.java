package com.itec.holzfaller.entities;

import com.lynden.gmapsfx.javascript.object.LatLong;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double latitude;
    private double longitude;
    private String color;

    public Location() {
    }

    public Location(String name, double latitude, double longitude, String color) {
        this.name = name;
        this.latitude = latitude;
        this.color = color;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLong computeLatLong() {
        return new LatLong(latitude, longitude);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
