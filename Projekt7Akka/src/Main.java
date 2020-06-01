import Actors.ClientActor;
import Actors.ServerActor;
import Database.DataBase;
import Model.ClientRequest;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static final ActorSystem actorSystem = ActorSystem.create("local_system");
    private static final ActorRef client = actorSystem.actorOf(Props.create(ClientActor.class), "client");
    private static final ActorRef server = actorSystem.actorOf(Props.create(ServerActor.class), "server");

    public static void simulateManyActors()
    {
        DataBase.createTable();
        List<String> products = new ArrayList<>(Arrays.asList("drink", "chips", "cola", "fish", "laptop","pen"));

        List<ActorRef> clients = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            clients.add(actorSystem.actorOf(Props.create(ClientActor.class), "client" + i));

        Random random = new Random();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 4; i++) {
                String prod1 = products.get(random.nextInt(products.size()));

                ActorRef client = clients.get(i);
                client.tell(new ClientRequest(prod1, server), null);
            }
            System.out.println("\n\n**********************\n");
            try
            { Thread.sleep(1000); }
            catch (InterruptedException e)
            { e.printStackTrace(); }
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Starting work, please enter your requests below. /n Enter \"s\" to simulate  or \"q\" to exit.");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            try
            {
                String input = bufferedReader.readLine();
                if (input.equals("q"))
                    break;
                else if (input.equals("s"))
                    simulateManyActors();
                else
                    client.tell(new ClientRequest(input,server), null);
            }
            catch (IOException e)
            {
                actorSystem.terminate();
                e.printStackTrace();
            }
        }
        actorSystem.terminate();
    }
}
