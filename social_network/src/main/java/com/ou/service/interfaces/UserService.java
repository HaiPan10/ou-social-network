package com.ou.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Account;
import com.ou.pojo.User;

public interface UserService {
    User create(User user, Account account);
    User uploadAvatar(MultipartFile uploadAvatar, Integer userId) throws IOException;
    User retrieve(Integer userId) throws Exception;
    Map<String, Object> loadProfile(Integer userId) throws Exception;
    Object uploadCover(MultipartFile uploadCover, Integer userId) throws IOException;
    Object updateUser(User user, Integer userId);
}
