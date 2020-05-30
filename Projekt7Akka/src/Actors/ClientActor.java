package Actors;

import Model.*;
import akka.actor.AbstractActor;

public class ClientActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ClientRequest.class, request -> request.server.tell(
                                new ServerRequest(request.getProduct(), getSelf() ), getSelf()
                        ))
                .match(ServerResponse.class, response -> {
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append("Actor ")
                            .append(this.getSelf().path().name())
                            .append(" received response:\n");
                    if(response.getState() == State.OK){
                        stringBuilder.append("Best price for ")
                                .append(response.getProductName())
                                .append(" is ")
                                .append(response.getPrice());
                    }
                    else{
                        stringBuilder.append("Time Out");
                    }
                    stringBuilder.append("\n");
                    System.out.println(stringBuilder.toString());
                })
                .match(ShopResponse.class,a->{
                    System.out.println("what the fuck!");
                })
                .matchAny(unknown ->{
                    System.err.println(unknown.toString() + " Unknown message");

                })
                .build();

    }
}
