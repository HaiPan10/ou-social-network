package com.ou.repository.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.UserStudentRepository;

@Repository
@Transactional
public class UserStudentRepositoryImpl implements UserStudentRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public UserStudent create(UserStudent userStudent) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        userStudent.setId(userStudent.getUser().getId());
        s.save(userStudent);
        return userStudent;
    }

    @Override
    public Optional<UserStudent> findByStudentIdentical(String studentIdentical) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserStudent> criteriaQuery = builder.createQuery(UserStudent.class);
        Root<UserStudent> root = criteriaQuery.from(UserStudent.class);
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("studentIdentical"), studentIdentical));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(criteriaQuery);
        try {
            return Optional.ofNullable((UserStudent) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
