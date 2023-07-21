package com.ou.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // @Autowired
    // private PassValidator passValidator;

    // @InitBinder
    // public void initBinder(WebDataBinder binder){
    //     binder.setValidator(passValidator);
    // }
    
    @PostMapping(path = "/register")
    public ResponseEntity<Object> register(@RequestBody Account account) throws Exception {
        try {
            return ResponseEntity.ok(accountService.create(account));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
