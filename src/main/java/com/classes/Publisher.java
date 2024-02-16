package com.classes;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {
    private static final String EXCHANGE = "MyExchange";

    public Publisher() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.72.128");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE, "fanout");
            Scanner input = new Scanner(System.in);
            String message;
            do {
                System.out.println("Enter message: ");
                message = input.nextLine();
                channel.basicPublish(EXCHANGE, "", null, message.getBytes());
            } while (!message.equalsIgnoreCase("Quit"));
        }
    }
}