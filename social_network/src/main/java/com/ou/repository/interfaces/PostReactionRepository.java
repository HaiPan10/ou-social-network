package com.ou.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.ou.pojo.PostReaction;
import com.ou.pojo.Reaction;
import com.ou.pojo.User;

public interface PostReactionRepository {
    List<PostReaction> countReaction(Integer postId);
    Optional<PostReaction> retrieve(Integer postReactionId);
    PostReaction saveOrUpdate(Integer postId, Integer userId, Reaction reaction) throws Exception;
    boolean delete(Integer postId, Integer userId) throws Exception;
    List<User> getReactionUsers(Integer postId, Integer reactionId);
}
