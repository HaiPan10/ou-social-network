package com.ou.repository.impl;

import java.util.ArrayList;
import java.util.List;

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

import com.ou.pojo.Account;
import com.ou.repository.interfaces.AccountRepository;

@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Account retrieve(Integer id) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("id"), id));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(criteriaQuery);
        return (Account) query.getSingleResult();
    }

    @Override
    public List<Account> list() {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root);

        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Account create(Account account) {
        Session s = this.sessionFactoryBean.getObject().getCurrentSession();
        account.setId((Integer) s.save(account));
        return account;
    }

    
}
