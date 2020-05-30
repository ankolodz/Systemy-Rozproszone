package Actors;

import Model.*;
import akka.actor.*;
import akka.japi.pf.DeciderBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static akka.actor.SupervisorStrategy.restart;

public class ServerActor extends AbstractActor {
    private int nextID;
    private Map<Integer, Task> actuallTask;

    ServerActor(){
        this.nextID = 1;
        this.actuallTask = new HashMap<>();
    }

    @Override
    public SupervisorStrategy supervisorStrategy(){
        return new OneForOneStrategy(
                10,
                Duration.ofSeconds(20),
                DeciderBuilder.matchAny(o->restart())
                .build()
        );
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()

                .match(ServerRequest.class, request->{
                    ActorRef shop1 = getContext().actorOf(Props.create(ShopActor.class),"shop_"+ nextID + "_1");
                    ActorRef shop2 = getContext().actorOf(Props.create(ShopActor.class),"shop_"+ nextID + "_2");
                    request.setTaskID(nextID);
                    nextID++;

                    shop1.tell(request,getSelf());
                    shop2.tell(request,getSelf());

                    actuallTask.put(nextID,new Task(nextID,request.getProductName(),request.getSender()));

                    getContext()
                            .system()
                            .scheduler()
                            .scheduleOnce(
                                    Duration.ofMillis(GlobalConstance.SERVER_TIMEOUT),
                                    getSelf(),
                                    new KillProcessing(request.getTaskID()),
                                    getContext().getSystem().dispatcher(),
                                    ActorRef.noSender()
                            );

                })
                .match(KillProcessing.class, process ->{
                    if (!actuallTask.containsKey(process.getId()))
                        return;
                    Task task = actuallTask.get(process.getId());
                    actuallTask.remove(process.getId());

                    ServerResponse response;
                    if (task.getPriceSize() != 0)
                        response = new ServerResponse(task.getProductName(), task.getBetterPrice(),State.OK);
                    else
                        response = new ServerResponse(task.getProductName(), -1,State.TIME_OUT);

                    task.getReciver().tell(response,getSelf());

                })
                .match(ShopResponse.class,shopResponse -> {
                    if(!actuallTask.containsKey(shopResponse.getId()))
                        return;
                    Task task = actuallTask.get(shopResponse.getId());
                    task.addPrice(shopResponse.getPrice());

                    if(task.getPriceSize() == 2){
                        actuallTask.remove(task.getID());
                        ServerResponse response = new ServerResponse(task.getProductName(), task.getBetterPrice(),State.OK);
                        task.getReciver().tell(response,getSelf());
                    }

                })
                .matchAny(unknown ->{
                    System.err.println("Unknown message");
                })
                .build();


    }
}
