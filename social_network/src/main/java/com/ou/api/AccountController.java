package com.ou.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Account;
import com.ou.service.interfaces.AccountService;
import com.ou.validator.PassValidator;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PassValidator passValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(passValidator);
    }
    
    @PostMapping(path = "/register")
    public Account register(@RequestBody @Valid Account account, BindingResult result) {
        System.out.println("This is user request:" + account);
        passValidator.validate(account, result);
         Account acc = accountService.create(account);
        System.out.println("This is saved account: " + acc);
        return acc;
    }
}
