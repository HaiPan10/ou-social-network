package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.Answer;
import com.ou.pojo.PostSurvey;
import com.ou.pojo.Response;
import com.ou.pojo.User;

public interface ResponseRepository {
    Response create(PostSurvey post, User user, Response response) throws Exception;
    List<Answer> getTextAnswers(Integer questionId);
}
