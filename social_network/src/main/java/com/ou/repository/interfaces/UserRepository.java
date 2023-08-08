package com.ou.repository.interfaces;

import java.util.Optional;

import com.ou.pojo.User;

public interface UserRepository {
    User create(User user);
    Optional<User> retrieve(Integer id);
    User updateAvatar(Integer userId, String url);
    User updateCover(Integer userId, String url);
    Object updateUser(User user, Integer userId);
}
