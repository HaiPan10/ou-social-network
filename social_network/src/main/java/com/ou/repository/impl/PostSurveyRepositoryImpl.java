package com.ou.repository.impl;

import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.cloud.storage.Option;
import com.ou.pojo.Post;
import com.ou.pojo.PostSurvey;
import com.ou.repository.interfaces.PostSurveyRepository;

@Repository
@Transactional
public class PostSurveyRepositoryImpl implements PostSurveyRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public PostSurvey create(PostSurvey postSurvey) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        postSurvey.setId((Integer)session.save(postSurvey));
        return postSurvey;
    }

    @Override
    public Optional<PostSurvey> retrieve(Integer id) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        PostSurvey postSurvey = session.get(PostSurvey.class, id);
        if(postSurvey != null){
            System.out.println("[DEBUG] - Post: " + postSurvey);
            return Optional.of(postSurvey);
        }

        return Optional.empty();
    }
     
}
