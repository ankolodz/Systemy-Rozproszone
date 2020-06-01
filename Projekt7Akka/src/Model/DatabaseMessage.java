package Model;

import akka.actor.ActorRef;

public class DatabaseMessage {
    private ActorRef server;
    private int taskID;
    private String productName;
    private int counter;

    public DatabaseMessage(ActorRef server, String productName, int id) {
        this.server = server;
        this.productName = productName;
        this.taskID = id;
    }

    public ActorRef getServer() {
        return server;
    }

    public String getProductName() {
        return productName;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public int getTaskID() {
        return taskID;
    }
}
