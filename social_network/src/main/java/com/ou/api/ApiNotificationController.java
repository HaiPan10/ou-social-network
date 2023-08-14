package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Notification;
import com.ou.service.interfaces.NotificationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/notification")
public class ApiNotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody Notification notification) throws Exception {
        return notificationService.pushNotification(notification);
    }
}
