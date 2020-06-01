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
                    ActorRef db = getContext().actorOf(Props.create(DbActor.class),"db_"+nextID);
                    request.setTaskID(nextID);
                    DatabaseMessage databaseMessage = new DatabaseMessage(getSelf(),request.getProductName(),nextID);
                    nextID++;

                    db.tell(databaseMessage,getSelf());
                    shop1.tell(request,getSelf());
                    shop2.tell(request,getSelf());


                    actuallTask.put(nextID,new Task(nextID,request.getProductName(),request.getSender()));

                    getContext()
                            .system()
                            .scheduler()
                            .scheduleOnce(
                                    Duration.ofMillis(GlobalConstains.SERVER_TIMEOUT),
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
                        response = new ServerResponse(task.getProductName(), task.getBetterPrice(),task.getDbCount(),State.OK);
                    else
                        response = new ServerResponse(task.getProductName(), -1,task.getDbCount(),State.TIME_OUT);
                    task.getReciver().tell(response,getSelf());

                })
                .match(ShopResponse.class,shopResponse -> {
                    if(!actuallTask.containsKey(shopResponse.getId()))
                        return;
                    Task task = actuallTask.get(shopResponse.getId());
                    task.addPrice(shopResponse.getPrice());

                    if(task.getPriceSize() == 2 && task.getDbCount() != -1){
                        send(task.getID());
                    }
                })
                .match(DatabaseMessage.class, db->{
                    if (!actuallTask.containsKey(db.getTaskID()))
                        return;
                    Task task = actuallTask.get(db.getTaskID());
                    task.setDbCount(db.getCounter());
                    if(task.getPriceSize() == 2){
                        send(task.getID());
                    }


                })
                .matchAny(unknown ->{
                    System.err.println("Unknown message");
                })
                .build();
    }
    private void send(int ID){
        if(!actuallTask.containsKey(ID))
            return;
        Task task = actuallTask.get(ID);
        actuallTask.remove(task.getID());
        ServerResponse response = new ServerResponse(task.getProductName(), task.getBetterPrice(),task.getDbCount(),State.OK);
        task.getReciver().tell(response,getSelf());
        }

}
