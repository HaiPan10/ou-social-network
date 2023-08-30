package com.ou.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Question;
import com.ou.pojo.QuestionOption;
import com.ou.repository.interfaces.QuestionRepository;

@Repository
@Transactional
public class QuestionRepositoryImpl implements QuestionRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public List<Question> create(List<Question> question) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        question.forEach(q -> {
            List<QuestionOption> questionOptions = q.getQuestionOptions();
            q.setQuestionOptions(null);
            q.setId((Integer) session.save("Question", q));
            if (questionOptions != null) {
                questionOptions.forEach(qo -> {
                    qo.setQuestionId(q);
                    qo.setId((Integer) session.save("QuestionOption", qo));
                });
                q.setQuestionOptions(questionOptions);
            }
        });
        return question;
    }

}
