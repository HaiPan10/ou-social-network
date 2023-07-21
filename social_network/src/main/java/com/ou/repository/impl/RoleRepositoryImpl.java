package com.ou.repository.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Role;
import com.ou.repository.interfaces.RoleRepository;

@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Role retrieve(Integer id) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        return (Role) s.get(Role.class, id);
    }
    
}
