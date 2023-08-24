package com.ou.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ou.configs.JwtService;
import com.ou.pojo.Post;
import com.ou.pojo.User;
import com.ou.service.interfaces.PostService;
import com.ou.utils.ValidationUtils;
import com.ou.validator.WebAppValidator;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "http://ousocialnetwork.id.vn/")
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
    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/upload")
    public ResponseEntity<Object> upLoadPost(String postContent,
     List<MultipartFile> images, boolean isActiveComment, HttpServletRequest httpServletRequest) throws Exception {
        try {
            Integer userId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.uploadPost(postContent, userId, images, isActiveComment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    ResponseEntity<Object> update(List<MultipartFile> images, @Valid Post post, boolean isEditImage,
     BindingResult bindingResult, HttpServletRequest httpServletRequest) throws Exception {
        webAppValidator.validate(post, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getInvalidMessage(bindingResult));
        }
        try {
            Integer userId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            post.setUserId(new User(userId));
            return ResponseEntity.ok(postService.update(post, images, isEditImage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "{postId}")
    ResponseEntity<Object> delete(@PathVariable Integer postId, HttpServletRequest httpServletRequest) {
        try {
            Integer userId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.delete(postId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    ResponseEntity<Object> loadNewFeed(HttpServletRequest httpServletRequest, @RequestParam Map<String, String> params) {
        try {
            Integer currentUserId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.ok(postService.loadNewFeed(currentUserId, params));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}