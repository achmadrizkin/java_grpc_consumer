package com.example.demo.service;

import com.example.demo.Notification;
import com.example.demo.model.InvoiceInfo;
import com.example.demo.model.PaymentInfo;
import com.example.demo.repository.InvoiceInfoRepository;
import com.example.demo.repository.PaymentInfoRepository;
import com.google.protobuf.util.JsonFormat;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    public static final String QUEUE_NAME = "myQueue";
    public static final String TYPE_PAYMENT_INFO = "PAYMENT_INFO";
    public static final String TYPE_INVOICE_INFO = "INVOICE_INFO";

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

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

            if (notification.getType().equals(TYPE_PAYMENT_INFO)) {
                PaymentInfo paymentInfo = new PaymentInfo(notification.getId(), notification.getTitle(), notification.getMessage());
                paymentInfoRepository.save(paymentInfo);
            } else if (notification.getType().equals(TYPE_INVOICE_INFO)) {
                InvoiceInfo paymentInfo = new InvoiceInfo(notification.getId(), notification.getTitle(), notification.getMessage());
                invoiceInfoRepository.save(paymentInfo);
            }
        } catch (Exception e) {
            System.err.println("Error deserializing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}