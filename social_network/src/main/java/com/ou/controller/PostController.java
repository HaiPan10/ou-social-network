package com.ou.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.InvitationGroup;
import com.ou.pojo.Post;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.InvitationGroupService;
import com.ou.service.interfaces.PostService;
import com.ou.service.interfaces.QuestionService;
import com.ou.service.interfaces.ResponseService;

@Controller
@RequestMapping("/admin/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private Environment env;
    @Autowired
    private AccountService accountService;
    @Autowired
    private InvitationGroupService invitationGroupService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ResponseService responseService;

    @GetMapping
    public String posts(Model model, @RequestParam Map<String, String> params) {
        List<Post> posts = postService.search(params);
        model.addAttribute("posts", posts);
        Integer pageSize = Integer.parseInt(env.getProperty("POST_PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(postService.countPosts(params) * 1.0 / pageSize));
        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);
            } else {
                page = 1;
            }

            String kw = params.get("kw");
            if (kw != null) {
                model.addAttribute("kw", kw);
            }

        } else {
            page = 1;
        }
        model.addAttribute("currentPage", page);
        if (params.get("status") != null) {
            model.addAttribute("status", params.get("status"));
        }
        return "posts";
    }

    @GetMapping("{id}")
    public String retrieve(@PathVariable(value = "id") Integer postId, Model model,
            @RequestParam Map<String, String> params) {
        try {
            Post post = postService.retrieve(postId);
            model.addAttribute("post", post);
            if (post.getPostSurvey() != null) {
                return "postSurveyDetail";
            } else if (post.getPostInvitation() != null) {
                System.out.println(post.getPostInvitation().getPostInvitationUsers());
                return "postInvitationDetail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "postDetail";
    }

    @GetMapping("/upload")
    public String uploadPost(Model model, @RequestParam(name = "status", required = false) String status) {
        Post post = new Post();
        model.addAttribute("post", post);
        List<Object[]> accountList = accountService.list();
        List<InvitationGroup> invitationGroups = invitationGroupService.list();
        model.addAttribute("accountList", accountList);
        model.addAttribute("invitationGroups", invitationGroups);
        // if (status != null) {
        // model.addAttribute("status", status);
        // }
        return "uploadPost";
    }

    @PostMapping("/upload")
    public String add(@ModelAttribute("post") Post post,
            @RequestPart(value = "images", required = false) List<MultipartFile> images, BindingResult bindingResult)
            throws Exception {
        try {
            postService.uploadPost(post.getContent(), 1, images, post.getIsActiveComment());
            return "redirect:/admin/posts?status=success";
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("exceptionError", e.getMessage()));
            return "uploadPost";
        }
    }

    @GetMapping(path = "/survey_question/{id}")
    public String statQuestion(Model model, @PathVariable Integer id) {
        List<Object[]> list = questionService.stat(id);
        if (list.isEmpty()) {
            model.addAttribute("listTextAnswer", responseService.getTextAnswers(id));
        }
        model.addAttribute("questionText", questionService.getText(id));
        model.addAttribute("id", id);

        return "questionDetail";
    }
}
