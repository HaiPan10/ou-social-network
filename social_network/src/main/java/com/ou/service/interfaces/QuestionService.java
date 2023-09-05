package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.PostSurvey;
import com.ou.pojo.Question;

public interface QuestionService {
    List<Question> create(PostSurvey postSurvey, List<Question> question);
    List<Object[]> stat(Integer questionId);
    Integer countAnswerByQuestionId(Integer questionId);
}
