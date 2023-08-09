package com.ou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.repository.interfaces.PostReactionRepository;
import com.ou.service.interfaces.PostReactionService;

@Service
public class PostReactionServiceImpl implements PostReactionService {
    @Autowired
    PostReactionRepository postReactionRepository;

    @Override
    public Integer countReaction(Integer postId) {
        return postReactionRepository.countReaction(postId);
    }
    
}
