package com.ou.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ou.service.interfaces.PostService;
import com.ou.validator.WebAppValidator;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/post")
public class ApiPostController {
    @Autowired 
    private PostService postService;

    @Autowired
    private WebAppValidator webAppValidator;

    @InitBinder("requestBody")
    public void initBinderWeb(WebDataBinder binder) {
        binder.setValidator(webAppValidator);
    }

    @PostMapping(path = "/upload/{userId}")
    public ResponseEntity<Object> upLoadPost(@PathVariable Integer userId, String postContent,
     List<MultipartFile> images) throws Exception {
        try {
            return ResponseEntity.ok(postService.uploadPost(postContent, userId, images));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "{userId}")
    ResponseEntity<Object> loadPost(@PathVariable Integer userId) throws Exception {
        try {
            return ResponseEntity.ok(postService.loadPost(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}