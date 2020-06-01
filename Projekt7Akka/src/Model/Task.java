package Model;

import akka.actor.ActorRef;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private int ID;
    private String productName;
    private List<Double> prices;
    private ActorRef reciver;
    private int dbCount = -1;


    public Task(int ID, String productName, ActorRef reciver) {
        this.ID = ID;
        this.productName = productName;
        this.reciver = reciver;
        this.prices = new ArrayList<>();
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getID() {
        return ID;
    }

    public String getProductName() {
        return productName;
    }

    public ActorRef getReciver() {
        return reciver;
    }

    public void addPrice(Double val){
        prices.add(val);
    }

    public Double getBetterPrice(){
        if(prices.size()>1)
            return prices.get(0)<prices.get(1) ? prices.get(0) : prices.get(1);
        if(prices.size()==1)
            return  prices.get(0);
        return Double.valueOf(-1);
    }

    public int getPriceSize(){
        return prices.size();
    }
}
