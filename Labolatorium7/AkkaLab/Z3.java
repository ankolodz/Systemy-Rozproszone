import akka.actor.ActorSystem;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class Z3 {

    public static void main(String[] argv) throws Exception {
        long start=System.currentTimeMillis();
        ActorSystem system = ActorSystem.create("stream_system");

        Source.range(1, 10)
                .map(val -> val * 2)
                .runWith(Sink.foreach(val -> System.out.println(val)), system);

        Source.from(Arrays.asList("hello", "hola", "hallo", "ola", "ahlan", "ni hao", "shalom", "salut", "ciao", "hug", "yasou"))
                .map(val -> val.toUpperCase())
                .runWith(Sink.foreach(val -> System.out.println(val)), system);

        Source.range(-5, -1)
                .map(val -> val * 2)
                .runWith(Sink.last(), system)
                .thenAccept(System.out::println);

        // TODO: final stream composition in task: source -> buffer -> map -> async -> map -> async -> runWith -> thenRun
        Source.range(1,10)
                .buffer(3, OverflowStrategy.backpressure())
                .map(val ->{
                    sleep(500);
                    val *= 10;
                    return val;
                })
                .async()
                .runWith(Sink.foreach(val -> System.out.println(val)), system)
                .thenRun(()->System.out.println("Time 1: " + (System.currentTimeMillis() - start)));

        Source.range(1,10)
                .buffer(3, OverflowStrategy.backpressure())
                .map(val ->{
                    sleep(500);
                    val *= 2;
                    return val;
                })
                .async()
                .runWith(Sink.foreach(val -> System.out.println(val)), system)
                .thenRun(()->System.out.println("Time 1: " + (System.currentTimeMillis() - start)));

            }





}
