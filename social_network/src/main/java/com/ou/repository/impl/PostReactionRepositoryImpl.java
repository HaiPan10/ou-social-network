package com.ou.repository.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.ou.service.interfaces.PostReactionService;

@Repository
@Transactional
public class PostReactionRepositoryImpl implements PostReactionService {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Integer countReaction(Integer postId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT Count(*) FROM PostReaction WHERE postId = :postId");
        query.setParameter("postId", postId);
        return Integer.parseInt(query.getSingleResult().toString());
    }
    
}
