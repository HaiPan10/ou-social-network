package com.ou.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ou.pojo.Account;
import com.ou.pojo.Post;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.PostService;
import com.ou.service.interfaces.PostSurveyService;

@RestController
@RequestMapping("admin")
public class ApiAdminController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostSurveyService postSurveyService;

    @PatchMapping("accounts/update_status")
    public ResponseEntity<?> update(@RequestBody Map<String, String> request){
        try {
            Account account = accountService.retrieve(Integer.parseInt(request.get("id")));
            if(!account.getStatus().equals(request.get("status"))){
                accountService.verifyAccount(account, request.get("status"));
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "posts/delete/{postId}")
    ResponseEntity<Object> delete(@PathVariable Integer postId) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.delete(postId, 1));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("posts/upload_survey")
    public ResponseEntity<?> uploadSurvey(@RequestBody Post post) {
        try {
            return ResponseEntity.ok().body(postService.uploadPostSurvey(post, 1));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "accounts/accounts")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(accountService.list());
    }

    @PostMapping(path = "posts/upload_invitation")
    public ResponseEntity<?> uploadInvitation(@RequestBody Post post) {
        try {
            return ResponseEntity.ok().body(postService.uploadPostInvitation(post, 1));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
