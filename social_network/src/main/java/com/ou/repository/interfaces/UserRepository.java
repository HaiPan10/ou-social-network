package com.ou.repository.interfaces;

import com.ou.pojo.User;

public interface UserRepository {
    User create(User user);
    User getUserById(Integer id);
    void updateAvatar(User id, String url);
}
