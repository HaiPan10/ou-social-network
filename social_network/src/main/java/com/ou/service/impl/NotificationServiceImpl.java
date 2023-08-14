package com.ou.service.impl;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.ou.pojo.Notification;
import com.ou.service.interfaces.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public String pushNotification(Notification notification) throws Exception {
        Message message = Message.builder()
                .setToken(notification.getTarget())
                .setNotification(
                    com.google.firebase.messaging.Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getBody())
                    .build()
                )
                .putData("content", notification.getTitle())
                .putData("body", notification.getBody())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new Exception("Fail to send firebase notification");
        }

        return response;
    }
    
}
