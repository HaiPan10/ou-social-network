package com.ou.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.ImageInPost;
import com.ou.pojo.Post;
import com.ou.repository.interfaces.PostRepository;
import com.ou.service.interfaces.CloudinaryService;
import com.ou.service.interfaces.CommentService;
import com.ou.service.interfaces.ImageInPostService;
import com.ou.service.interfaces.PostReactionService;
import com.ou.service.interfaces.PostService;
import com.ou.utils.CloudinaryUtils;

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
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Post uploadPost(String postContent, Integer userId, List<MultipartFile> images, boolean isActiveComment) throws Exception {
        Post newPost = new Post();
        if (postContent.length() == 0 && images == null) {
            throw new Exception("Empty post!");
        }
        newPost.setContent(postContent);
        newPost.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setIsActiveComment(isActiveComment);
        postRepository.uploadPost(newPost, userId);
        if (images != null) {
            newPost.setImageInPostList(imageInPostService.uploadImageInPost(images, newPost));
        }
        postReactionService.countReaction(newPost, userId);
        newPost.setCommentTotal(commentService.countComment(newPost.getId()));
        return newPost;
    }

    @Override
    public List<Post> loadPost(Integer userId, Integer currentUserId, Map<String, String> params) throws Exception {
        Optional<List<Post>> listPostOptional = postRepository.loadPost(userId, params);
        if (listPostOptional.isPresent()) {
            List<Post> posts = listPostOptional.get();
            posts.forEach(p -> {
                postReactionService.countReaction(p, currentUserId);
                p.setCommentTotal(commentService.countComment(p.getId()));
                p.getImageInPostList().forEach(img -> img.setContentType(String.format("image/%s", CloudinaryUtils.getImageType(img.getImageUrl()))));
            });
            return posts;
        } else {
            throw new Exception("User không hợp lệ!");
        }
    }

    @Override
    public boolean update(Post post, List<MultipartFile> images, boolean isEditImage) throws Exception {
        Post persistPost = retrieve(post.getId());
        if (!persistPost.getUserId().getId().equals(post.getUserId().getId())) {
            throw new Exception("Not owner");
        }
        else if (isEditImage) {
            List<ImageInPost> imageInPostList = persistPost.getImageInPostList();
            if (imageInPostList.size() != 0) {
                imageInPostService.deleteImageInPost(imageInPostList);
            }
            if (images != null) {
                persistPost.setImageInPostList(imageInPostService.uploadImageInPost(images, persistPost));
            } else {
                imageInPostList.clear();
                persistPost.setImageInPostList(imageInPostList);
            }
        } else {
        }
        return postRepository.update(persistPost, post);
    }

    @Override
    public Post retrieve(Integer postId) throws Exception {
        Optional<Post> postOptional = postRepository.retrieve(postId);
        if (postOptional.isPresent()) {
            return postOptional.get();
        } else {
            throw new Exception("Không tìm thấy bài đăng!");
        }
    }

    @Override
    public boolean delete(Integer postId, Integer userId) throws Exception {
        Post persistPost = retrieve(postId);
        if (!persistPost.getUserId().getId().equals(userId) && userId != 1) {
            throw new Exception("Not owner");
        }
        List<String> oldImageUrls = persistPost.getImageInPostList().stream().map(img -> img.getImageUrl()).collect(Collectors.toList());
        System.out.println("GOT PERSIST: " + persistPost);        
        if (postRepository.delete(persistPost)) {
            oldImageUrls.forEach(oldImage -> {
                try {
                    cloudinaryService.deleteImage(oldImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return true;
    }

    @Override
    public List<Post> loadNewFeed(Integer currentUserId, @RequestParam Map<String, String> params) throws Exception {
        Optional<List<Post>> listPostOptional = postRepository.loadNewFeed(params);
        if (listPostOptional.isPresent() && listPostOptional.get().size() != 0) {
            List<Post> posts = listPostOptional.get();
            posts.forEach(p -> {
                postReactionService.countReaction(p, currentUserId);
                p.setCommentTotal(commentService.countComment(p.getId()));
                p.getImageInPostList().forEach(img -> img.setContentType(String.format("image/%s", CloudinaryUtils.getImageType(img.getImageUrl()))));
            });
            return posts;
        } else {
            throw new Exception("No more post");
        }
    }

    @Override
    public List<Post> list(Map<String, String> params) {
        return postRepository.list(params);
    }

    @Override
    public Integer countPosts() {
        return postRepository.countPosts();
    }
}
