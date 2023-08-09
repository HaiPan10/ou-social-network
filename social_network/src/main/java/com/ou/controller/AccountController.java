package com.ou.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.service.interfaces.AccountService;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    private Environment env;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/verification/")
    public String accountsVerification(Model model, @RequestParam Map<String, String> params) {
        List<Account> pendingAccounts = accountService.getPendingAccounts(params);
        model.addAttribute("pendingAccounts", pendingAccounts);
        Integer pageSize = Integer.parseInt(env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(accountService.countPendingAccounts() * 1.0 / pageSize));
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
        return "accounts";
    }

    @GetMapping("/verification/{accountId}")
    public String verify(@PathVariable Integer accountId, @RequestParam String status) throws Exception {
        accountService.verifyAccount(accountService.retrieve(accountId), status);
        return "redirect:/admin/accounts/verification/";
    }

    @GetMapping("/provider")
    public String provideAccounts(ModelMap model) {
        Account account = new Account();
        account.setUser(new User());
        model.addAttribute("account", account);
        return "accountProvider";
    }

    @PostMapping("/provider")
    public String add(@Valid @ModelAttribute("account") Account account, BindingResult accountBindingResult) throws Exception {
        try {
            User user = account.getUser();
            account.setUser(null);
            System.out.printf("[INFO] - Account Provider: %s\n", account);
            System.out.printf("[INFO] - User Provider: %s\n", user);
            if (accountBindingResult.hasErrors()) {
                String invalidMessage = accountBindingResult
                        .getAllErrors()
                        .get(0)
                        .getDefaultMessage();
                System.out.printf("[INFO] - Error: %s\n", invalidMessage);
                return "redirect:/admin/accounts/provider/";
            }

            Account createdAccount = accountService.create(account, user);
            System.out.printf("[INFO] - Provider email: %s\n", createdAccount);
            return "redirect:/admin/accounts/provider/";
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
