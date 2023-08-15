package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.BatchResponse;
import com.ou.pojo.Notice;
import com.ou.service.interfaces.FirebaseService;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "http://34.101.76.209:80")
@CrossOrigin(origins = "*")
@RequestMapping("api/notification")
public class ApiNotificationController {
    @Autowired
    private FirebaseService notificationService;

    @PostMapping("")
    public BatchResponse sendNotification(@RequestBody Notice notice) throws Exception {
        return notificationService.sendNotification(notice);
    }
}
