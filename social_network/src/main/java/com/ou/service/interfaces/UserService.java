package com.ou.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Account;
import com.ou.pojo.User;

public interface UserService {
    User create(User user, Account account);
    User uploadAvatar(MultipartFile uploadAvatar, Integer userId) throws IOException, Exception;
    User retrieve(Integer userId) throws Exception;
    Map<String, Object> loadProfile(Integer userId, Integer currentUserId, Map<String, String> params) throws Exception;
    Object uploadCover(MultipartFile uploadCover, Integer userId) throws IOException, Exception;
    Object updateUser(User user, Integer userId);
    List<User> list(List<Integer> listUserId);
    List<User> list();
}
