package com.ou.repository.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.ou.pojo.Post;

public interface PostRepository {
    Post uploadPost(Post post, Integer userId) throws Exception;
    Optional<List<Post>> loadPost(Integer userId, @RequestParam Map<String, String> params);
    boolean update(Post persistPost, Post post);
    Optional<Post> retrieve(Integer postId) throws Exception;
    boolean delete(Post persistPost);
    Optional<List<Post>> loadNewFeed(Integer currentUserId, @RequestParam Map<String, String> params);
    List<Post> list(Map<String, String> params);
    Integer countPosts(Map<String, String> params);
    List<Post> search(Map<String, String> params);
}
