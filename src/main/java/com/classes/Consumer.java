package com.classes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.bson.Document;

public class Consumer {
    private static String QUEUE = "MyFirstQueue";


    public Consumer() throws IOException, TimeoutException {
        MongoDBConnectionHandlerImpl mongo = new MongoDBConnectionHandlerImpl();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(mongo.rabbitHost);
        factory.setPort(mongo.rabbitPort);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE, false, false, false, null);
        System.out.println("Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            Document doc = new Document().append("masssage", message);
            mongo.mongoDatabase.getCollection("Massages").insertOne(doc);

            System.out.println("Received '" + message + "'");
        };
        channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> { });
    }
}