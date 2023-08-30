package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.Post;
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

    @Override
    public PostSurvey retrieve(Integer postId) throws Exception{
        System.out.println("[DEBUG] - INSIDE POST SURVEY SERVICE");
        Optional<PostSurvey> post = postSurveyRepository.retrieve(postId);
        if(post.isPresent()){
            return post.get();
        }else{
            throw new Exception("Post don't exists");
        }
    }
    
}
