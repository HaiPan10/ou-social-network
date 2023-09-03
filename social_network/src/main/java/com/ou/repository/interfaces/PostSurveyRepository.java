package com.ou.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.ou.pojo.PostSurvey;

public interface PostSurveyRepository {
    PostSurvey create(PostSurvey postSurvey);
    List<Object[]> stat(Map<String, String> params);
}
