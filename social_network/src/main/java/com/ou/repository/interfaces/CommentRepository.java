package com.ou.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.ou.pojo.Comment;
import com.ou.pojo.Post;
import com.ou.pojo.User;

public interface CommentRepository {
    Integer countComment(Integer postId);
    Comment create(Comment comment, Post persistPost, User persistUser);
    List<Comment> loadComment(Integer postId);
    Optional<Comment> retrieve(Integer commentId);
    Comment editComment(Comment persistComment, Comment comment);
    boolean delete(Comment persistComment);
}
