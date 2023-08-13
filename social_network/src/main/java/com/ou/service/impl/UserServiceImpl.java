package com.ou.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.repository.interfaces.UserRepository;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.PostService;
import com.ou.service.interfaces.CloudinaryService;
import com.ou.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private PostService postService;

    @Override
    public User create(User user, Account account) {
        user.setAccount(account);
        return userRepository.create(user);
    }

    @Override
    public User uploadAvatar(MultipartFile uploadAvatar, Integer userId) throws Exception {
        try {
            String newUrl = cloudinaryService.uploadImage(uploadAvatar);
            User persistUser = retrieve(userId);
            String oldUrl = persistUser.getAvatar();
            User returnUser = userRepository.updateAvatar(persistUser, newUrl);
            if (oldUrl != null) {
                cloudinaryService.deleteImage(oldUrl);
            }
            return returnUser;
        } catch (IOException e) {
            throw new IOException("Fail to upload avatar");
        }
    }

    @Override
    public User uploadCover(MultipartFile uploadCover, Integer userId) throws Exception {
        try {
            String newUrl = cloudinaryService.uploadImage(uploadCover);
            User persistUser = retrieve(userId);
            String oldUrl = persistUser.getCoverAvatar();
            User returnUser = userRepository.updateCover(persistUser, newUrl);
            if (oldUrl != null) {
                cloudinaryService.deleteImage(oldUrl);
            }
            return returnUser;
        } catch (IOException e) {
            throw new IOException("Fail to upload avatar");
        }
    }

    @Override
    public User retrieve(Integer userId) throws Exception {
        Optional<User> userOptional = userRepository.retrieve(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new Exception("Không tìm thấy người dùng");
        }
    }

    @Override
    public Map<String, Object> loadProfile(Integer userId) throws Exception {
        Account retrieveAccount = accountService.retrieve(userId);
        if (!retrieveAccount.getStatus().equals("ACTIVE")) {
            throw new Exception("Not activated Account!");
        }
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("user", retrieveAccount.getUser());
        jsonObject.put("role", retrieveAccount.getRoleId());
        jsonObject.put("posts", postService.loadPost(userId));
        return jsonObject;
    }

    @Override
    public Object updateUser(User user, Integer userId) {
        return userRepository.updateUser(user, userId);
    }
    
}
