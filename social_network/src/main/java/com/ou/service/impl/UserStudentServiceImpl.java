package com.ou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.UserStudent;
import com.ou.repository.UserStudentRepository;
import com.ou.service.UserStudentService;

@Service
public class UserStudentServiceImpl implements UserStudentService {
    @Autowired
    private UserStudentRepository userStudentRepository;

    @Override
    public UserStudent create(UserStudent userStudent) {
        return userStudentRepository.create(userStudent);
    }


}
