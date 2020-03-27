package com.company;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Administrator {

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private String EXCHANGE_NAME = "exchange1";


    public static void main(String[] args) throws IOException, TimeoutException {

        Administrator administrator = new Administrator();
        administrator.run();
    }

    public void run() throws IOException, TimeoutException {
        System.out.println("Administrator is ready");
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
            System.out.println("Enter send to: ");
            String name = br.readLine();

            if (!name.equals("all"))
                name = "all." + name;
            System.out.println(name);
            Order order = new Order(OrderType.ADMIN,"Administrator",-1);
            // publish
            channel.basicPublish(EXCHANGE_NAME, name, null, order.toByte());

        }

    }


    private void messageHandler() throws IOException, TimeoutException {

        // queue & bind
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "all.#.#");

        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                Order order = Order.createFromBythe(body);
                System.out.println("Message: " + order.toString());
            }
        };

        // start listening
        channel.basicConsume(queueName, true, consumer);
    }
}
