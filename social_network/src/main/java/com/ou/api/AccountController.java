package com.ou.api;

import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
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
    public ResponseEntity<Object> createPendingAccount(@RequestBody Map<String, Object> params) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.convertValue(params.get("account"), Account.class);
            User user = mapper.convertValue(params.get("user"), User.class);
            UserStudent userStudent = mapper.convertValue(params.get("userStudent"), UserStudent.class);
            return ResponseEntity.ok(accountService.createPendingAccount(account, user, userStudent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
