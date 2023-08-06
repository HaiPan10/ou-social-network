package com.ou.service.interfaces;

import java.io.IOException;

import com.ou.pojo.Account;
import com.ou.pojo.User;

public interface UserService {
    User create(User user, Account account);
    void uploadAvatar(User user) throws IOException;
    User getUserById(Integer userId);
}
