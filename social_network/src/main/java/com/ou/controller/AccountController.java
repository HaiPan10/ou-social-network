package com.ou.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ou.pojo.Account;
import com.ou.service.interfaces.AccountService;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {  
    @Autowired
    AccountService accountService;
    @Autowired
    private Environment env;
    
    @GetMapping("/verification")
    public String accountsVerification(Model model, @RequestParam Map<String, String> params) {
        List<Account> pendingAccounts = accountService.getPendingAccounts(params);
        System.out.println(pendingAccounts.toString());
        model.addAttribute("pendingAccounts", pendingAccounts);
        Integer pageSize = Integer.parseInt(env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(accountService.countPendingAccounts() * 1.0/pageSize));
        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);                
            } else {
                page = 1;
            }
        } else {
            page = 1;
        }
        model.addAttribute("currentPage", page);
        return "accountsVerification";
    }

    @GetMapping
    public String accounts(Model model) {
        model.addAttribute("pendingAccount", new Account(1, "abc@gmail.com", "123456"));
        return "accounts";
    }

    @GetMapping("/verification/{accountId}")
    public String verify(@PathVariable Integer accountId, @RequestParam String status) {
        accountService.verifyAccount(accountService.retrieve(accountId), status);
        return "redirect:/admin/accounts/verification";
    }
}
