package com.ou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.repository.interfaces.CommentRepository;
import com.ou.service.interfaces.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Integer countComment(Integer postId) {
        return commentRepository.countComment(postId);
    }
    
}
