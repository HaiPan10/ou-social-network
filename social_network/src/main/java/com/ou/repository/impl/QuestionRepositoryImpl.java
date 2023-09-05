package com.ou.repository.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.AnswerOption;
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

    @Override
    public List<Object[]> stat(Integer questionId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<QuestionOption> root = criteriaQuery.from(QuestionOption.class);
        Join<QuestionOption, AnswerOption> join = root.join("answerOptions", JoinType.LEFT);

        criteriaQuery.multiselect(root.get("id"), root.get("value"), builder.count(join.get("id")))
                .where(builder.equal(root.get("questionId").get("id"), questionId)).groupBy(root.get("id"));

        Query query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Integer countUnchoiceOption(Integer questionId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT COUNT(*) FROM Answer a "
                + "LEFT JOIN AnswerOption ao on a.id = ao.answerId.id "
                + "WHERE a.questionId.id = :questionId and ao.id IS NULL");
        query.setParameter("questionId", questionId);

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public String getText(Integer id) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT questionText from Question where id = :id");
        query.setParameter("id", id);
        return query.getSingleResult().toString();
    }
}
