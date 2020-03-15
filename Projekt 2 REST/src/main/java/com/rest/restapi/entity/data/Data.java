package com.rest.restapi.entity.data;

import java.util.List;

public class Data {
    private String key;
    private List<Values> values;

    public Data() {
    }

    public Data(String key, List<Values> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}
