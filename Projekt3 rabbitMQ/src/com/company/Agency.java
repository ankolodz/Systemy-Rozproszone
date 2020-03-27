package com.company;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Agency {
    private String name;
    private int id = 0;
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private String EXCHANGE_NAME = "exchange1";

    public Agency(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Podaj nazwę");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String name = in.readLine();

        Agency agency = new Agency (name);
        agency.run();
    }

    public void run() throws IOException, TimeoutException {
        System.out.println("Agency is ready");
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

        //createOrderPanel
        orderPanel();
    }

    private void orderPanel() throws IOException {

        while (true) {

            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter orderType: ");
            String orderString = br.readLine();

            OrderType orderType = OrderType.valueOf(orderString.toUpperCase());
            Order order = new Order(orderType,name, nextID());

            // publish
            channel.basicPublish(EXCHANGE_NAME, "deliver."+order.getTypeName(), null, order.toByte());

        }

    }

    private int nextID() {
        id++;
        return id;
    }

    private void messageHandler() throws IOException, TimeoutException {
        //key
        String key1 = "agency."+name;
        String key2 = "agency";

        // queue & bind
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, key1);
        channel.queueBind(queueName, EXCHANGE_NAME, key2);

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                Order order = Order.createFromBythe(body);
                System.out.println("Done" + order.toString());
            }
        };

        // start listening
        channel.basicConsume(queueName, true, consumer);
    }


}
