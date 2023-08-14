package com.ou.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@PropertySource("classpath:configs.properties")
public class IndexController {

    @RequestMapping("/")
    public String index(Model model){
        return "index";
    }

    @RequestMapping("/admin/dashboard")
    public String dashBoard(Model model){
        return "dashBoard";
    }
}
