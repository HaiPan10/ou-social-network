package com.ou.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.InvitationGroup;
import com.ou.repository.interfaces.InvitationGroupRepository;

@Repository
@Transactional
public class InvitationGroupRepositoryImpl implements InvitationGroupRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public List<InvitationGroup> list() {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM InvitationGroup");
        return query.getResultList();
    }
    
}
