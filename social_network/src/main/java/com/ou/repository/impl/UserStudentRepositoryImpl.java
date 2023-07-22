package com.ou.repository.impl;


import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.UserStudentRepository;

@Repository
@Transactional
public class UserStudentRepositoryImpl implements UserStudentRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public UserStudent create(UserStudent userStudent) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        userStudent.setId(userStudent.getUser().getId());
        s.save(userStudent);
        return userStudent;
    }
}
