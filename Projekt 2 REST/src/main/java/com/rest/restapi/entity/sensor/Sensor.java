package com.rest.restapi.entity.sensor;

public class Sensor {
    private int id;
    private int stationId;
    private Param param;

    public Sensor() {
    }

    public Sensor(int id, int stationId, Param param) {
        this.id = id;
        this.stationId = stationId;
        this.param = param;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }
}
