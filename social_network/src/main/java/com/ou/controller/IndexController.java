package com.ou.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@PropertySource("classpath:configs.properties")
public class IndexController {

    /* REMEMBER TO REMOVE THIS */
    @Autowired
    private HttpServletResponse response;

    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxLGFkbWluNDU2QGdtYWlsLmNvbSIsImlhdCI6MTY5MTkwNTQ3NSwiZXhwIjoxNjkzMjE5NDc1fQ.PNTkYw-_hwqil3ZtdlocsgwkfKZpR1WjcTgOB4OXOeciaofwEwG-mhvVvZPXy-DLK_5nWjOM2G3vdAV9bcH7JQ";

    /*=========================*/

    @RequestMapping("/")
    public String index(Model model){
        /* REMEMBER TO REMOVE THIS */
        Cookie cookie = new Cookie("Authorization", token);
        response.addCookie(cookie);
        /*=========================*/
        return "index";
    }

    @RequestMapping("/admin/dashboard")
    public String dashBoard(Model model){
        return "dashBoard";
    }
}
