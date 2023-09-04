package com.ou.repository.interfaces;

import com.ou.pojo.PostSurvey;
import com.ou.pojo.Response;
import com.ou.pojo.User;

public interface ResponseRepository {
    Response create(PostSurvey post, User user, Response response) throws Exception;
}
