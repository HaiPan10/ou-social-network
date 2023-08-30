package com.ou.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;
import com.ou.repository.interfaces.GroupRepository;

@Repository
@Transactional
public class GroupRepositoryImpl implements GroupRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public InvitationGroup create(InvitationGroup invitationGroup) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        invitationGroup.setId((Integer) session.save(invitationGroup));
        return invitationGroup;
    }

    @Override
    public void addUsers(List<User> users) throws Exception {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        
    }
    
}
