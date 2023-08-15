package com.ou.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Post;
import com.ou.service.interfaces.PostService;
import com.ou.utils.ValidationUtils;
import com.ou.validator.WebAppValidator;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "http://34.101.76.209:80")
@CrossOrigin(origins = "*")
@RequestMapping("api/posts")
public class ApiPostController {
    @Autowired 
    private PostService postService;

    @Autowired
    private WebAppValidator webAppValidator;

    @InitBinder()
    public void initBinderWeb(WebDataBinder binder) {
        binder.setValidator(webAppValidator);
    }

    @PostMapping(path = "/upload/{userId}")
    public ResponseEntity<Object> upLoadPost(@PathVariable Integer userId, String postContent,
     List<MultipartFile> images, boolean isActiveComment) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.uploadPost(postContent, userId, images, isActiveComment));
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
    
    @PostMapping
    ResponseEntity<Object> update(List<MultipartFile> images, @Valid Post post, boolean isEditImage, BindingResult bindingResult) throws Exception {
        webAppValidator.validate(post, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getInvalidMessage(bindingResult));
        }
        try {
            return ResponseEntity.ok(postService.update(post, images, isEditImage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "{postId}")
    ResponseEntity<Object> delete(@PathVariable Integer postId) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.delete(postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}