package com.ou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.UserStudentRepository;
import com.ou.service.interfaces.UserStudentService;

@Service
public class UserStudentServiceImpl implements UserStudentService {
    @Autowired
    private UserStudentRepository userStudentRepository;

    @Override
    public UserStudent create(UserStudent userStudent, User user) throws Exception {
        userStudent.setUser(user);
        if (userStudentRepository.findByStudentIdentical(userStudent.getStudentIdentical()).isPresent()) {
            throw new Exception("Mã số sinh viên đã được sử dụng!");
        }
        return userStudentRepository.create(userStudent);
    }
}
