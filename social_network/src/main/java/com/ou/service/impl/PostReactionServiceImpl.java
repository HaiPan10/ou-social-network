package com.ou.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.Post;
import com.ou.pojo.PostReaction;
import com.ou.pojo.Reaction;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostReactionRepository;
import com.ou.service.interfaces.PostReactionService;

@Service
public class PostReactionServiceImpl implements PostReactionService {
    @Autowired
    PostReactionRepository postReactionRepository;

    @Override
    public void countReaction(Post returnPost, Integer currentUser) {
        List<PostReaction> postReactions = postReactionRepository.countReaction(returnPost.getId());
        Optional<PostReaction> reactionOptional = postReactions.stream().filter(p -> p.getUserId().getId().equals(currentUser)).findFirst();
        if (reactionOptional.isPresent()) {
            returnPost.setCurrentReaction(reactionOptional.get().getReactionId());
        }
        returnPost.setReactionTotal(postReactions.stream()
        .collect(Collectors.groupingBy(PostReaction::getReactionId, Collectors.counting())));
    }

    @Override
    public PostReaction reaction(Integer postId, Integer userId, Reaction reaction) throws Exception {
        return postReactionRepository.saveOrUpdate(postId, userId, reaction);
    }

    @Override
    public boolean delete(Integer postId, Integer userId) throws Exception {
        return postReactionRepository.delete(postId, userId);
    }

    @Override
    public List<User> getReactionUsers(Integer postId, Integer reactionId) {
        return postReactionRepository.getReactionUsers(postId, reactionId);
    }
    
}
