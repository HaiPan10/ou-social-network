package com.ou.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                p.getImageInPostList().forEach(img -> img.setContentType(String.format("image/%s", CloudinaryUtils.getImageType(img.getImageUrl()))));
            });
            return posts;
        } else {
            throw new Exception("Lỗi user không hợp lệ!");
        }
    }

    @Override
    public boolean update(Post post, List<MultipartFile> images, boolean isEditImage) throws Exception {
        Post persistPost = retrieve(post.getId());

        // check is the owner of the post through authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.getName().equals(persistPost.getUserId().getAccount().getEmail())){
            System.out.println("[DEBUG] - Authentication email: " + authentication.getName());
            System.out.println("[DEBUG] - Request email: " + persistPost.getUserId().getAccount().getEmail());
            throw new Exception("Not owner");
        }

        if (isEditImage) {
            System.out.println("EDIT IMAGE");
            List<ImageInPost> imageInPostList = persistPost.getImageInPostList();
            if (imageInPostList.size() != 0) {
                System.out.println("DELETE OLD IMAGES");
                imageInPostService.deleteImageInPost(imageInPostList);
            }
            if (images != null) {
                System.out.println("UPLOAD NEW IMAGES");
                persistPost.setImageInPostList(imageInPostService.uploadImageInPost(images, persistPost));
            } else {
                System.out.println("SET FOR NO IMAGES");
                imageInPostList.clear();
                persistPost.setImageInPostList(imageInPostList);
            }
        } else {
            System.out.println("NO CHANGE FOR IMAGE");
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
    public boolean delete(Integer postId) throws Exception {
        Post persistPost = retrieve(postId);
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
}
