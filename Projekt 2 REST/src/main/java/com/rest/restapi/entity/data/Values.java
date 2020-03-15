package com.rest.restapi.entity.data;

import java.util.Date;

public class Values {
    private String date;
    private Double value;

    public Values() {
    }

    public Values(String date, Double value) {
        this.date = date;
        this.value = value;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
