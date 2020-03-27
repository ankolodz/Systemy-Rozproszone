package com.company;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Deliver {

    private OrderType firstSpecialisation, secondSpecialisation;
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private String EXCHANGE_NAME = "exchange1";

    public Deliver(OrderType first, OrderType second) {
        this.firstSpecialisation = first;
        this.secondSpecialisation = second;
    }
///to do
    //zrobić 3 kolejki dla typów, a nie po 2 do każdego klienta
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Podaj obsługiwane typy zleceń");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String firstType = in.readLine();
        String secondType = in.readLine();

        Deliver deliver = new Deliver (OrderType.valueOf(firstType),OrderType.valueOf(secondType));
        deliver.run();
    }

    public void run() throws IOException, TimeoutException {
        System.out.println("Deliver is ready");
        // connection & channel
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //messageHandler
        Thread thread = new Thread(() ->{
            try {
                messageHandler();
            } catch (IOException e) {
                System.out.println("Błąd kolejki");
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("serwer nie osiągalny");
                e.printStackTrace();
            }
        });
        thread.run();

    }

    private void messageHandler() throws IOException, TimeoutException {
        //key
        String key1 = "deliver."+firstSpecialisation.getName();
        String key2 = "deliver."+secondSpecialisation.getName();

        // queue & bind
        channel.queueDeclare(firstSpecialisation.getName(), false, false, false, null);
        channel.queueDeclare(secondSpecialisation.getName(), false, false, false, null);
        
        channel.queueBind(firstSpecialisation.getName(), EXCHANGE_NAME, key1);
        channel.queueBind(secondSpecialisation.getName(), EXCHANGE_NAME, key2);
        channel.basicQos(1);

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                Order order = Order.createFromBythe(body);
                System.out.println("Working" + order.toString());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                order.work();
                channel.basicPublish(EXCHANGE_NAME, "agency."+order.getStationName(), null, order.toByte());
            }
        };

        // start listening
        channel.basicConsume(firstSpecialisation.getName(), true, consumer);
        channel.basicConsume(secondSpecialisation.getName(), true, consumer);
    }


}
