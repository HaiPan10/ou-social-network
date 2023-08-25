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
import com.ou.pojo.Post;
import com.ou.service.interfaces.PostService;

@Controller
@RequestMapping("/admin/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private Environment env;

    @GetMapping
    public String posts(Model model, @RequestParam Map<String, String> params) {
        List<Post> posts = postService.list(params);
        model.addAttribute("posts", posts);
        Integer pageSize = Integer.parseInt(env.getProperty("POST_PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(postService.countPosts() * 1.0 / pageSize));
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
        return "posts";
    }

    @GetMapping("{id}")
    public String retrieve(@PathVariable(value = "id") Integer postId, Model model, @RequestParam Map<String, String> params) {
        try {
            Post post = postService.retrieve(postId);
            model.addAttribute("post", post);
        } catch (Exception e) {

        }
        return "postDetail";
    }
}
