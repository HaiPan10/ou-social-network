package com.ou.repository.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
     
}
