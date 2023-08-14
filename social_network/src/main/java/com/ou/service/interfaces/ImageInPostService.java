package com.ou.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.ImageInPost;
import com.ou.pojo.Post;

public interface ImageInPostService {
    List<ImageInPost> uploadImageInPost(List<MultipartFile> imageList, Post newPost);
    void deleteImageInPost(List<ImageInPost> imageInPosts);
}
