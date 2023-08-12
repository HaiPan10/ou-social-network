package com.ou.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.User;
import com.ou.service.interfaces.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/update_avatar/{userId}")
    public ResponseEntity<Object> updateAvatar(MultipartFile uploadAvatar, @PathVariable Integer userId){
        try {
            return ResponseEntity.ok().body(userService.uploadAvatar(uploadAvatar, userId));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/update_cover/{userId}")
    public ResponseEntity<Object> updateCover(MultipartFile uploadCover, @PathVariable Integer userId){
        try {
            return ResponseEntity.ok().body(userService.uploadCover(uploadCover, userId));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(value = "update_information/{userId}")
    public ResponseEntity<Object> updateInformation(@RequestBody User user, @PathVariable Integer userId){
        try {
            System.out.println(user.getDob());
            return ResponseEntity.ok().body(userService.updateUser(user, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Object> loadProfile(@PathVariable Integer userId) {
        try {
            return ResponseEntity.ok().body(userService.loadProfile(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
