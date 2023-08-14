package com.ou.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Post;

public interface PostService {
    Post uploadPost(String postContent, Integer userId, List<MultipartFile> image, boolean isActiveComment) throws Exception;
    List<Post> loadPost(Integer userId) throws Exception;
    boolean update(Post post, List<MultipartFile> images, boolean isEditImage) throws Exception;
    Post retrieve(Integer postId) throws Exception;
}
