package com.ou.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Account;
import com.ou.pojo.Post;
import com.ou.pojo.PostSurvey;
import com.ou.repository.interfaces.PostSurveyRepository;

@Repository
@Transactional
public class PostSurveyRepositoryImpl implements PostSurveyRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public PostSurvey create(PostSurvey postSurvey) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        postSurvey.setId((Integer)session.save(postSurvey));
        return postSurvey;
    }

    @Override
    public List<Object[]> stat(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<PostSurvey> root = criteriaQuery.from(PostSurvey.class);
        Join<PostSurvey, Post> join = root.join("post");

        Expression<Integer> expression = null;

        String month = params.get("byMonth");
        String quarter = params.get("byQuarter");
        if (month != null && month.equals("true")) {
            expression = builder.function("MONTH", Integer.class, join.get("createdAt"));
        } else if (quarter != null && quarter.equals("true")) {
            expression = builder.function("QUARTER", Integer.class, join.get("createdAt"));
        } else {
            expression = builder.function("YEAR", Integer.class, join.get("createdAt"));
        }

        List<Predicate> predicates = new ArrayList<>();

        String year = params.get("year");
        if (year != null) {
            predicates.add(builder.equal(builder.function("YEAR", Integer.class, join.get("createdAt")),
                    Integer.valueOf(year)));
        }

        criteriaQuery.multiselect(expression, builder.count(root.get("id")))
                .where(predicates.toArray(Predicate[]::new)).groupBy(expression).orderBy(builder.asc(expression));

        Query query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }
     
}
