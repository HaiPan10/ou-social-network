package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Post;
import com.ou.repository.interfaces.PostRepository;
import com.ou.service.interfaces.CommentService;
import com.ou.service.interfaces.ImageInPostService;
import com.ou.service.interfaces.PostReactionService;
import com.ou.service.interfaces.PostService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageInPostService imageInPostService;
    @Autowired
    private PostReactionService postReactionService;
    @Autowired
    private CommentService commentService;

    @Override
    public Post uploadPost(String postContent, Integer userId, List<MultipartFile> images, boolean isActiveContent) throws Exception {
        Post newPost = new Post();
        newPost.setContent(postContent);
        newPost.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setIsActiveComment(isActiveContent);
        postRepository.uploadPost(newPost, userId);
        newPost.setImageInPostList(imageInPostService.uploadImageInPost(images, newPost));
        newPost.setReactionTotal(postReactionService.countReaction(newPost.getId()));
        newPost.setCommentTotal(commentService.countComment(newPost.getId()));
        return newPost;
    }

    @Override
    public List<Post> loadPost(Integer userId) throws Exception {
        Optional<List<Post>> listPostOptional = postRepository.loadPost(userId);
        if (listPostOptional.isPresent()) {
            List<Post> posts = listPostOptional.get();
            posts.forEach(p -> {
                p.setReactionTotal(postReactionService.countReaction(p.getId()));
                p.setCommentTotal(commentService.countComment(p.getId()));
            });
            return posts;
        } else {
            throw new Exception("Lỗi user không hợp lệ!");
        }
    }
}
