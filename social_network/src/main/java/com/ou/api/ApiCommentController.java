package com.ou.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ou.pojo.Comment;
import com.ou.service.interfaces.CommentService;
import com.ou.utils.ValidationUtils;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/comments")
public class ApiCommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(path = "/{postId}/{userId}")
    ResponseEntity<Object> create(@Valid @RequestBody Comment comment,
     @PathVariable Integer postId, @PathVariable Integer userId, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            System.out.println("Got Error");
            return ResponseEntity.badRequest().body(ValidationUtils.getInvalidMessage(bindingResult));
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(comment, postId, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
