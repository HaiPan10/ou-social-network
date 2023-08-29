package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.PostSurvey;
import com.ou.repository.interfaces.PostSurveyRepository;
import com.ou.service.interfaces.PostSurveyService;

@Service
public class PostSurveyServiceImpl implements PostSurveyService{
    @Autowired
    private PostSurveyRepository postSurveyRepository;

    @Override
    public PostSurvey uploadPostSurvey(Integer postId, PostSurvey postSurvey) throws Exception {
        postSurvey.setStartAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        postSurvey.setId(postId);
        return postSurveyRepository.create(postSurvey);
    }
    
}
