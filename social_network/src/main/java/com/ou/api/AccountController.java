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

    @Autowired
    private PassValidator passValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(passValidator);
    }
    
    @PostMapping(path = "/register")
    public ResponseEntity<Account> register(@RequestBody @Valid Account account,
    BindingResult result) throws Exception {
        passValidator.validate(account, result);
        if(result.hasErrors()){
            return new ResponseEntity<>(
                account,
                HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>( 
            accountService.create(account),
            HttpStatus.CREATED
        );        
    }
}
