package com.rest.restapi.entity.station;

import com.rest.restapi.entity.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private int id;
    private String stationName;
    private String gegrLat;
    private String gegrLon;
    private City city;
    private String addressStreet;
    private List<Sensor> sensors;

    public Station() {
    }

    public Station(int id, String stationName, String gegrLat, String gegrLon, City city, String addressStreet) {
        this.id = id;
        this.stationName = stationName;
        this.gegrLat = gegrLat;
        this.gegrLon = gegrLon;
        this.city = city;
        this.addressStreet = addressStreet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getGegrLat() {
        return gegrLat;
    }

    public void setGegrLat(String gegrLat) {
        this.gegrLat = gegrLat;
    }

    public String getGegrLon() {
        return gegrLon;
    }

    public void setGegrLon(String gegrLon) {
        this.gegrLon = gegrLon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
}
