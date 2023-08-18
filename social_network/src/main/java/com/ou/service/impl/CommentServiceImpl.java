package com.ou.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.Comment;
import com.ou.pojo.Post;
import com.ou.pojo.User;
import com.ou.repository.interfaces.CommentRepository;
import com.ou.service.interfaces.CommentService;
import com.ou.service.interfaces.PostService;
import com.ou.service.interfaces.UserService;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Override
    public Integer countComment(Integer postId) {
        return commentRepository.countComment(postId);
    }

    @Override
    public Comment create(Comment comment, Integer postId, Integer userId) throws Exception {
        User persistUser = userService.retrieve(userId);
        Post persistPost = postService.retrieve(postId);
        if (!persistPost.getIsActiveComment()) {
            throw new Exception("Bài đăng này bị khóa bình luận");
        } else {
            return commentRepository.create(comment, persistPost, persistUser);
        }
    }

    @Override
    public List<Comment> loadComment(Integer postId) {
        return commentRepository.loadComment(postId);
    }

    

    @Override
    public Comment editComment(Comment comment) throws Exception {
        Comment persistComment = retrieve(comment.getId());
        return commentRepository.editComment(persistComment, comment);
    }

    @Override
    public Comment retrieve(Integer commentId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.retrieve(commentId);
        if (commentOptional.isPresent()) {
            return commentOptional.get();
        } else {
            throw new Exception("Không tìm thấy comment!");
        }
    }

    @Override
    public boolean delete(Integer commentId) throws Exception {
        Comment persistComment = retrieve(commentId);
        return commentRepository.delete(persistComment);
    }
}
