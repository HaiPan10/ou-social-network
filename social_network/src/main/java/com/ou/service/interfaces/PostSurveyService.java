package com.ou.service.interfaces;

import com.ou.pojo.PostSurvey;

public interface PostSurveyService {
    PostSurvey uploadPostSurvey(Integer postId, PostSurvey postSurvey) throws Exception;
}
