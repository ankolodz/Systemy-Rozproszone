package com.company;

import java.text.ParseException;

public enum OrderType {
    PASSENGER("PASSENGER"),
    SATELLITE("SATELLITE"),
    CARGO("CARGO"),
    AGENCY(""),
    DELIVER("");

    private final String name;

    OrderType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static void printAllName(){
        for (OrderType i:OrderType.values()) {
            System.out.println(i.getName());
        }
    }


}
