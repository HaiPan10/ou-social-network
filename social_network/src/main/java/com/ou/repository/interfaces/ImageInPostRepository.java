package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.ImageInPost;

public interface ImageInPostRepository {
    List<ImageInPost> uploadImage(List<ImageInPost> imageInPosts);
    boolean deleteImageInPost(List<ImageInPost> imageInPosts);
}
