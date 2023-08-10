package com.ou.repository.impl;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.repository.interfaces.CommentRepository;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {
@Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Integer countComment(Integer postId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT Count(*) FROM Comment WHERE postId.id = :postId");
        query.setParameter("postId", postId);
        return Integer.parseInt(query.getSingleResult().toString());
    }
    
}
