package com.ou.repository.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.checkerframework.checker.units.qual.s;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Answer;
import com.ou.pojo.AnswerOption;
import com.ou.pojo.Post;
import com.ou.pojo.PostSurvey;
import com.ou.pojo.Question;
import com.ou.pojo.QuestionOption;
import com.ou.pojo.Response;
import com.ou.pojo.User;
import com.ou.repository.interfaces.ResponseRepository;

@Repository
public class ResponseRepositoryImpl implements ResponseRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response create(PostSurvey postSurvey, User user, Response response) throws Exception {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        try {
            Response transientResponse = new Response();
            transientResponse.setUserId(user);
            transientResponse.setSurveyId(postSurvey);
            transientResponse.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            session.save(transientResponse);

            response.getAnswers().forEach(answer -> {
                Question persistQuestion = session.get(Question.class, answer.getQuestionId().getId());
                if (persistQuestion == null) {
                    try {
                        throw new Exception("In valid question!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Answer transientAnswer = new Answer();
                transientAnswer.setQuestionId(persistQuestion);
                transientAnswer.setValue(answer.getValue());
                transientAnswer.setResponseId(transientResponse);
                session.save(transientAnswer);
                
                if (answer.getAnswerOptions() != null) {
                    answer.getAnswerOptions().forEach(answerOption -> {
                        AnswerOption transientAnswerOption = new AnswerOption();
                        QuestionOption persistQuestionOption = session.get(QuestionOption.class, answerOption.getQuestionOptionId().getId());
                        if (persistQuestionOption == null) {
                            try {
                                throw new Exception("In valid question option!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        transientAnswerOption.setQuestionOptionId(persistQuestionOption);
                        transientAnswerOption.setAnswerId(transientAnswer);
                        session.save(transientAnswerOption);
                    });
                }
            });
            return transientResponse;         
        } catch (HibernateException ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
}
