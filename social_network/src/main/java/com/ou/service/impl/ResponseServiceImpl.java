package com.ou.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Post;
import com.ou.pojo.Response;
import com.ou.pojo.User;
import com.ou.repository.interfaces.ResponseRepository;
import com.ou.service.interfaces.PostService;
import com.ou.service.interfaces.ResponseService;
import com.ou.service.interfaces.UserService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResponseServiceImpl implements ResponseService{
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseRepository responseRepository;

    @Override
    public Response create(Response response) throws Exception {
        Post persistPost = postService.retrieve(response.getSurveyId().getId());
        User persistUser = userService.retrieve(response.getUserId().getId());
        return responseRepository.create(persistPost.getPostSurvey(), persistUser, response);
    }
    
}
