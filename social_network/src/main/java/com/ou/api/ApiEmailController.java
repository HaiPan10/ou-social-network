package com.ou.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ou.pojo.Account;
import com.ou.service.interfaces.MailService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/email")
public class ApiEmailController {
    @Autowired
    private MailService mailService;

    @PostMapping(path = "/verify")
    public void SendVerificationEmail(@RequestBody Map<String, Object> params) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.convertValue(params.get("account"), Account.class);
            mailService.sendVerificationEmail(account);
        } catch (Exception e) {
            return ;
        }
    }
}
