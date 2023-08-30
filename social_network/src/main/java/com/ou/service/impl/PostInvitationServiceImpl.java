package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.PostInvitation;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostInvitationRepository;
import com.ou.service.interfaces.PostInvitationService;

@Service
public class PostInvitationServiceImpl implements PostInvitationService{
    @Autowired
    private PostInvitationRepository postInvitationRepository;

    @Override
    public PostInvitation create(Integer postId, PostInvitation postInvitation, List<User> listUsers) {
        postInvitation.setStartAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        postInvitation.setId(postId);
        return postInvitationRepository.create(postInvitation, listUsers);
    }
    
}
