package Actors;

import Model.GlobalConstains;
import Model.ServerRequest;
import Model.ShopResponse;
import akka.actor.AbstractActor;

import java.util.Random;

public class ShopActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ServerRequest.class, request -> {
                    Random random = new Random();
                    Thread.sleep(Math.abs(random.nextInt()%500));
                    ShopResponse shopResponse = new ShopResponse(request.getTaskID(),Math.abs(random.nextDouble()*1000% GlobalConstains.MAX_PRICE));
                    getSender().tell(shopResponse,null);
                    getContext().stop(getSelf());
                })
                .matchAny(unknown->{
                    System.err.println("Unknown message");
                })

                .build();
    }
}
