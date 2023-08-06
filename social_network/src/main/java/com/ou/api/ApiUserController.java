package com.ou.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ou.pojo.User;
import com.ou.service.interfaces.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "update_avatar",
        method = RequestMethod.POST
    )
    public ResponseEntity<String> updateAvatar(User user){
        try {
            System.out.println("[DEBUG] - " + user);
            userService.uploadAvatar(user);
            return ResponseEntity.ok().body("Upload Successful");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> get(@PathVariable Integer userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }
}
