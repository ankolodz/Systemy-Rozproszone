package com.rest.restapi.REST;

public class Buffer {
    private double sum;
    private int n;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void add(double adder){
        sum+=adder;
        n++;
    }
}
