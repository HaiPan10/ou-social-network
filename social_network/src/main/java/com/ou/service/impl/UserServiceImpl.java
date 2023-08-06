package com.ou.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.repository.interfaces.UserRepository;
import com.ou.service.interfaces.UploadFileService;
import com.ou.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public User create(User user, Account account) {
        user.setAccount(account);
        return userRepository.create(user);
    }

    @Override
    public void uploadAvatar(User user) throws IOException {
        try {
            String url = uploadFileService.uploadImage(user.getUploadAvatar());
            userRepository.updateAvatar(user, url);
        } catch (IOException e) {
            throw new IOException("Fail to upload avatar");
        }
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }
    
}
