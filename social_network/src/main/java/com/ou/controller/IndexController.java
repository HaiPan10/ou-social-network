package com.ou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    // @Autowired
    // private AccountService accountService;

    @RequestMapping("/")
    public String index(Model model){
        // Account account = accountService.getAccountById(1L);
        String msg = "Success";
        // if(account != null){
        //     model.addAttribute("account", account);
        // }
        // else {
        //     msg = "Failure";
        // }
        model.addAttribute("msg", msg);
        return "index";
    }
}
