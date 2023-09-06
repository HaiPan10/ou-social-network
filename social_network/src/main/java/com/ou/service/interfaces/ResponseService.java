package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.Answer;
import com.ou.pojo.Response;

public interface ResponseService {
    Response create(Response response) throws Exception;
    List<Answer> getTextAnswers(Integer questionId);
}
