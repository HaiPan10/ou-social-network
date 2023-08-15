package com.ou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import com.ou.pojo.Notice;
import com.ou.service.interfaces.FirebaseService;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Override
    public BatchResponse sendNotification(Notice notice) throws Exception {
        List<String> registrationTokens= notice.getRegistrationTokens();
        Notification notification = Notification.builder()
                .setTitle(notice.getSubject())
                .setBody(notice.getContent())
                .setImage(notice.getImage())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(notification)
                .putAllData(notice.getData())
                .build();

        BatchResponse batchResponse = null;
        try {
            batchResponse = firebaseMessaging.sendMulticast(message);
        } catch (FirebaseMessagingException e) {
            throw new Exception(String.format("Firebase error {%s}", e.getMessage()));
        }
        if (batchResponse.getFailureCount() > 0) {
            List<SendResponse> responses = batchResponse.getResponses();
            List<String> failedTokens = new ArrayList<>();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    failedTokens.add(registrationTokens.get(i));
                }
            }
            throw new Exception(String.format("List of tokens that caused failures: {%s}", failedTokens));
        }
        return batchResponse;
    }    
}
