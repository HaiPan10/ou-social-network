package com.ou.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.ImageInPost;
import com.ou.pojo.Post;
import com.ou.repository.interfaces.ImageInPostRepository;
import com.ou.service.interfaces.ImageInPostService;
import com.ou.service.interfaces.UploadFileService;

@Service
public class ImageInPostServiceImpl implements ImageInPostService {
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private ImageInPostRepository imageInPostRepository;

    @Override
    public List<ImageInPost> uploadImageInPost(List<MultipartFile> imageList, Post newPost) {
        List<String> imageUrl = imageList.parallelStream()
        .map(img -> {
            try {
                return uploadFileService.uploadImage(img);
            } catch (IOException e) {
                try {
                    throw new Exception("Up ảnh thất bại");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        })
        .filter(url -> url != null)
        .collect(Collectors.toList());

        List<ImageInPost> imageInPosts = imageUrl.stream().map(img -> {
            ImageInPost imageInPost = new ImageInPost();
            imageInPost.setImageUrl(img);
            imageInPost.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            imageInPost.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            imageInPost.setPostId(newPost);
            return imageInPost;
        })
        .collect(Collectors.toList());

        return imageInPostRepository.uploadImage(imageInPosts);
    }

    @Override
    public boolean deleteImageInPost(List<ImageInPost> imageInPosts) {
        return imageInPostRepository.deleteImageInPost(imageInPosts);
    }
}

