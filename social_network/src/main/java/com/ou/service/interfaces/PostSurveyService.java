package com.ou.service.interfaces;

import java.util.List;
import java.util.Map;

import com.ou.pojo.PostSurvey;

public interface PostSurveyService {
    PostSurvey uploadPostSurvey(Integer postId, PostSurvey postSurvey) throws Exception;
    List<Object[]> stat(Map<String, String> params);
}
