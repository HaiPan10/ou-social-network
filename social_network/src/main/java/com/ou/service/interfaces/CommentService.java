package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.Comment;

public interface CommentService {
    Integer countComment(Integer postId);
    Comment create(Comment comment, Integer postId, Integer userId) throws Exception;
    List<Comment> loadComment(Integer postId);
}
