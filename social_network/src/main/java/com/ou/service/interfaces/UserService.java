package com.ou.service.interfaces;

import com.ou.pojo.Account;
import com.ou.pojo.User;

public interface UserService {
    User create(User user, Account account);
}
