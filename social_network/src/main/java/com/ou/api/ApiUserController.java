package com.ou.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ou.configs.JwtService;
import com.ou.pojo.User;
import com.ou.service.interfaces.UserService;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
// @CrossOrigin(origins = "http://ousocialnetwork.id.vn/")
// @CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class ApiUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/update_avatar")
    public ResponseEntity<Object> updateAvatar(MultipartFile uploadAvatar, HttpServletRequest httpServletRequest) throws Exception{
        try {
            Integer userId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.ok().body(userService.uploadAvatar(uploadAvatar, userId));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/update_cover")
    public ResponseEntity<Object> updateCover(MultipartFile uploadCover, HttpServletRequest httpServletRequest) throws Exception{
        try {
            Integer userId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.ok().body(userService.uploadCover(uploadCover, userId));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(value = "/update_information")
    public ResponseEntity<Object> updateInformation(@RequestBody User user, HttpServletRequest httpServletRequest){
        try {
            Integer userId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.ok().body(userService.updateUser(user, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Object> loadProfile(@PathVariable Integer userId, HttpServletRequest httpServletRequest, @RequestParam Map<String, String> params) {
        try {
            Integer currentUserId = Integer.parseInt(jwtService.getAccountId(httpServletRequest));
            return ResponseEntity.ok().body(userService.loadProfile(userId, currentUserId, params));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
