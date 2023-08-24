package com.ou.api;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.configs.JwtService;
import com.ou.service.interfaces.MailService;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "http://34.101.76.209:80")
// @CrossOrigin(origins = "http://ousocialnetwork.id.vn/")
@CrossOrigin(origins = "*")
@RequestMapping("api/email")
public class ApiEmailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtService jwtService;

    @GetMapping(path = "/verify")
    public void SendVerificationEmail(HttpServletRequest httpServletRequest) throws Exception {
        try {
            Integer accountId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            mailService.sendVerificationEmail(accountId);
        } catch (Exception e) {
            return ;
        }
    }
}
