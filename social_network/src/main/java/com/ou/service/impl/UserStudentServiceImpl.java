package com.ou.service.impl;

import org.hibernate.exception.ConstraintViolationException;
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
        try {
            userStudent.setUser(user);
            System.out.println("CREATE FROM USERSTUDENT SERVICE");
            return userStudentRepository.create(userStudent);
        } catch (ConstraintViolationException e) {
            System.out.println("EXCEPTION FROM USERSTUDENT SERVICE");
            throw new Exception("Mã số sinh viên này đã được sử dụng!");
        }
    }


}
