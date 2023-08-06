package com.ou.repository.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.User;
import com.ou.repository.interfaces.UserRepository;

@Repository
@Transactional
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

    @Override
    public void updateAvatar(User user, String url) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        User persistUser = s.get(User.class, user.getId());
        persistUser.setAvatar(url);
        s.saveOrUpdate(persistUser);
    }

    @Override
    public User getUserById(Integer id) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        return (User) s.get(User.class, id);
    }
    
}
