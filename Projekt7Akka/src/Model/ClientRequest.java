package Model;

import akka.actor.ActorRef;

public class ClientRequest {
    public final  ActorRef server;
    private String product;
    public ClientRequest(String product, ActorRef server) {
        this.server = server;
        this.product = product;
    }

    public String getProduct() {
        return product;
    }
}
