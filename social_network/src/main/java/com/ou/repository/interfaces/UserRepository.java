package com.ou.repository.interfaces;

import java.util.Optional;

import com.ou.pojo.User;

public interface UserRepository {
    User create(User user);
    Optional<User> retrieve(Integer id);
    User updateAvatar(User persistUser, String url);
    User updateCover(User persistUser, String url);
    Object updateUser(User user, Integer userId);
}
