package com.ou.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.Post;

public interface PostService {
    Post uploadPost(String postContent, Integer userId, List<MultipartFile> image, boolean isActiveComment) throws Exception;
    List<Post> loadPost(Integer userId, Integer currentUserId, Map<String, String> params) throws Exception;
    List<Post> list(Map<String, String> params);
    boolean update(Post post, List<MultipartFile> images, boolean isEditImage) throws Exception;
    Post retrieve(Integer postId) throws Exception;
    boolean delete(Integer postId, Integer userId) throws Exception;
    List<Post> loadNewFeed(Integer currentUserId, @RequestParam Map<String, String> params) throws Exception;
    Integer countPosts(Map<String, String> params);
    List<Post> search(Map<String, String> params);
    Post uploadPostSurvey(Post post, Integer userId) throws Exception;
    Post uploadPostInvitation(Post post, Integer userId) throws Exception;
}
