package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.Question;

public interface QuestionRepository {
    List<Question> create(List<Question> question);
    List<Object[]> stat(Integer questionId);
    Integer countUnchoiceOption(Integer questionId);
    String getText(Integer id);
}
