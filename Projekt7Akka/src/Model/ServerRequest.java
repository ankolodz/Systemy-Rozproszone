package Model;

import akka.actor.ActorRef;

public class ServerRequest {
    private String productName;
    private int taskID;
    private ActorRef sender;



    public ServerRequest(String productName, ActorRef sender) {
        this.productName = productName;
        this.sender = sender;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getProductName() {
        return productName;
    }

    public int getTaskID() {
        return taskID;
    }

    public ActorRef getSender() {
        return sender;
    }
}
