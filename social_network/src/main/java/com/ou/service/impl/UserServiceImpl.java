package com.ou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.repository.interfaces.UserRepository;
import com.ou.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user, Account account) {
        user.setAccount(account);
        return userRepository.create(user);
    }
    
}
