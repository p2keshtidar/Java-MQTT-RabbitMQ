package com.classes;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    private static String QUEUE = "MyFirstQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE, false, false, false, null);

            Scanner input = new Scanner(System.in);
            String message;
            do {
                System.out.println("Enter message: ");
                message = input.nextLine();
                channel.basicPublish("", QUEUE, null, message.getBytes());
            } while (!message.equalsIgnoreCase("Quit"));
        }
    }
}
