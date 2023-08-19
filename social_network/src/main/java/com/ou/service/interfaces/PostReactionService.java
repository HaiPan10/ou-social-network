package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.Post;
import com.ou.pojo.PostReaction;
import com.ou.pojo.Reaction;
import com.ou.pojo.User;

public interface PostReactionService {
    void countReaction(Post returnPost, Integer currentUser);
    PostReaction reaction(Integer postId, Integer userId, Reaction reaction) throws Exception;
    boolean delete(Integer postId, Integer userId) throws Exception;
    List<User> getReactionUsers(Integer postId, Integer reactionId);
}
