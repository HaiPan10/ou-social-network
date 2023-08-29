package com.ou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.PostSurvey;
import com.ou.pojo.Question;
import com.ou.repository.interfaces.QuestionRepository;
import com.ou.service.interfaces.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> create(PostSurvey postSurvey, List<Question> question) {
        question.forEach(q -> q.setSurveyId(postSurvey));
        return questionRepository.create(question);
    }
    
}
