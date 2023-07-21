package com.ou.repository.impl;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.User;
import com.ou.repository.interfaces.UserRepository;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public User create(User user) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        user.setId(user.getAccount().getId());
        s.save(user);
        return user;
    }
    
}
