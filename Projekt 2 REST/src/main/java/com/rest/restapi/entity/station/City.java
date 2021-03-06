package com.rest.restapi.entity.station;

public class City {
    private int id;
    private String name;
    private Commune commune;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public City() {
    }

    public City(int id, String name, Commune commune) {
        this.id = id;
        this.name = name;
        this.commune = commune;
    }
}
