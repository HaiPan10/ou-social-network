package com.ou.repository.impl;


import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.UserStudentRepository;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class UserStudentRepositoryImpl implements UserStudentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public UserStudent create(UserStudent userStudent) {
        Session s = this.factory.getObject().getCurrentSession();
        UserStudent returnUserStudent = (UserStudent) s.save(userStudent);
        return returnUserStudent;
    }
}
