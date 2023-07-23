package com.ou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ou.pojo.Account;

@Controller
public class AccountController {    
    @GetMapping("/accounts")
    public String list(Model model) {
        model.addAttribute("pendingAccount", new Account(1, "abc@gmail.com", "123456"));
        return "accounts";
    }
}
