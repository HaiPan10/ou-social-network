package com.ou.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.ou.pojo.Post;

public interface PostRepository {
    Post uploadPost(Post post, Integer userId) throws Exception;
    Optional<List<Post>> loadPost(Integer userId);
}
