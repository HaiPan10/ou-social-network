package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.service.interfaces.AccountService;

@RestController
@RequestMapping("/api")
public class AccountsController {
    @Autowired
    private AccountService accountService;
}
