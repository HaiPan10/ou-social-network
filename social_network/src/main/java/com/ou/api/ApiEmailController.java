package com.ou.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.service.interfaces.MailService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/email")
public class ApiEmailController {
    @Autowired
    private MailService mailService;

    @GetMapping(path = "/verify/{accountId}")
    public void SendVerificationEmail(@PathVariable Integer accountId) throws Exception {
        try {
            mailService.sendVerificationEmail(accountId);
        } catch (Exception e) {
            return ;
        }
    }
}
