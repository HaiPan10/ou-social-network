package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Account;
import com.ou.service.interfaces.AccountService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    
    @PostMapping(path = "/register")
    public Account register(@RequestBody Account account) {
        System.out.printf("DEBUG FROM CONTROLLER: %s\n", account.toString());
        return accountService.create(account);
    }
}
