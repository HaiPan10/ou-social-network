package com.ou.service.interfaces;

import com.ou.pojo.Notification;

public interface NotificationService {
    String pushNotification(Notification notification) throws Exception;    
}
