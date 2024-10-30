package com.example.demo.service;

import com.example.demo.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    public static final String QUEUE_NAME = "myQueue";
    public static final String EXCHANGE_NAME = "notification-topic";
    public static final String ROUTING_KEY = "my.routing.key";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveNotification(String message) {
        try {
            Notification.Builder notificationBuilder = Notification.newBuilder();
            JsonFormat.parser().merge(message, notificationBuilder);
            Notification notification = notificationBuilder.build();

            System.out.println("--- Received Notification ---");
            System.out.println("ID: " + notification.getId());
            System.out.println("Title: " + notification.getTitle());
            System.out.println("Message: " + notification.getMessage());
            System.out.println("Type: " + notification.getType());
        } catch (Exception e) {
            System.err.println("Error deserializing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}