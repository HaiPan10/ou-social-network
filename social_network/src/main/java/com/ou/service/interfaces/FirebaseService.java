package com.ou.service.interfaces;

import com.google.firebase.messaging.BatchResponse;
import com.ou.pojo.Notice;

public interface FirebaseService {
    BatchResponse sendNotification(Notice notice) throws Exception;    
}
