package com.ou.repository.interfaces;

import java.util.Optional;

import com.ou.pojo.Post;
import com.ou.pojo.PostSurvey;

public interface PostSurveyRepository {
    PostSurvey create(PostSurvey postSurvey);
    Optional<PostSurvey> retrieve(Integer id);
}
