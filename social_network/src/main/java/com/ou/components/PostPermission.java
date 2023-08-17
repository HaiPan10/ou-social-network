package com.ou.components;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ou.pojo.Post;
import com.ou.service.interfaces.PostService;


public class PostPermission implements PermissionEvaluator {
    // @Autowired
    // private PostService postService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetObject, Object permission) {
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetPost, String targetType,
            Object permission) {
        // if ("Post".equals(targetType) && "edit".equals(permission)) {
        //     Post post = null;
        //     if (targetPost instanceof Post) {
        //         post = (Post) targetPost;
        //     } else {
        //         return false;
        //     }

        //     String email = authentication.getPrincipal().toString();
        //     try {
        //         persistPost = postService.retrieve(post.getId());
        //     } catch (Exception e) {
        //         return false;
        //     }
        //     if (targetPost.getUserId().getAccount().getEmail().equals(email)) {

        //     }
            
        // }
        return true;
    }
}
