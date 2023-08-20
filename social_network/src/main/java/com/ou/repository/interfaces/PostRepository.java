package com.ou.repository.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.ou.pojo.Post;

public interface PostRepository {
    Post uploadPost(Post post, Integer userId) throws Exception;
    Optional<List<Post>> loadPost(Integer userId);
    boolean update(Post persistPost, Post post);
    Optional<Post> retrieve(Integer postId);
    boolean delete(Post persistPost);
    Optional<List<Post>> loadNewFeed(@RequestParam Map<String, String> params);
}
