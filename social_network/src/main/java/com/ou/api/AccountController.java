package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.repository.UserStudentRepository;
import com.ou.service.AccountService;
import com.ou.service.UserStudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserStudentService userStudentService;

    @PostMapping(path = "/register")
    public UserStudent register(@RequestBody UserStudent userStudent) {
        return userStudentService.create(userStudent);
    }
}
