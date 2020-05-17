package com.company.Agency;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.concurrent.TimeoutException;

public class Agency {
    String name;

    public Agency(String name){
        this.name = name;
    }
    public void run(){

    }
    public void messageHandler(){
        Thread thread = new Thread(()->{
            try {
                // connection & channel
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                // exchange
                String EXCHANGE_NAME = "exchange1";
                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            }
            catch (TimeoutException e) {
                System.out.println("Błąd połączenia z rabbitMQ");
                e.printStackTrace();
            }catch (IOException e){
                System.out.println("Błąd kolejki");
                e.printStackTrace();
            }
        });
        thread.start();
    }
    public void orderPanel(){

    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Agency agency = new Agency(in.readLine());
        System.out.println("Agency started");
        agency.run();
    }
}
