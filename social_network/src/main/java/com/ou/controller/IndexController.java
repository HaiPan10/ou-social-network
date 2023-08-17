package com.ou.controller;

import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@PropertySource("classpath:configs.properties")
public class IndexController {

    @RequestMapping("/")
    public String index(@RequestParam Map<String, String> map, Model model){
        // if(map.get("error") != null){
        //     model.addAttribute("error", map.get("error"));
        // }
        return "index";
    }

    @RequestMapping("/admin/dashboard")
    public String dashBoard(Model model){
        return "dashBoard";
    }
}
