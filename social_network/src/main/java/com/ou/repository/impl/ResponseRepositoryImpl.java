package com.ou.repository.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Post;
import com.ou.pojo.PostSurvey;
import com.ou.pojo.Response;
import com.ou.pojo.User;
import com.ou.repository.interfaces.ResponseRepository;

@Repository
@Transactional
public class ResponseRepositoryImpl implements ResponseRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Response create(PostSurvey postSurvey, User user, Response response) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        // response.setSurveyId(postSurvey);
        // response.setUserId(user);
        // response.setId(null);
        return new Response();
    }
    
}
