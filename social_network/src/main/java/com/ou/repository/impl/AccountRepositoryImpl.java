package com.ou.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    @Autowired
    private Environment env;

    @Override
    public Optional<Account> retrieve(Integer id) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        try {
            return Optional.ofNullable((Account) (Account) session.get(Account.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> list(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
    
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        // role id = 3 is admin role
        predicates.add(builder.notEqual(root.get("roleId"), 3));

        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);                
            } else {
                page = 1;
            }

            String kw = params.get("kw");
            if(kw != null){
                predicates.add(builder.like(root.get("email"), String.format("%%%s%%", kw)));
            }

        } else {
            page = 1;
        }
        int pageSize = Integer.parseInt(this.env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));

        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(criteriaQuery);

        query.setMaxResults(pageSize);
        query.setFirstResult((page - 1) * pageSize);
        return query.getResultList();
    }

    @Override
    public Account create(Account account) {
        Session s = this.sessionFactoryBean.getObject().getCurrentSession();
        account.setId((Integer) s.save(account));
        return account;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("email"), email));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(criteriaQuery);
        try {
            return Optional.ofNullable((Account) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> getPendingAccounts(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.get("status"), "AUTHENTICATION_PENDING"));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(criteriaQuery);

        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);                
            } else {
                page = 1;
            }
        } else {
            page = 1;
        }
        int pageSize = Integer.parseInt(this.env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));

        query.setMaxResults(pageSize);
        query.setFirstResult((page - 1) * pageSize);
        return query.getResultList();
    }

    @Override
    public Integer countPendingAccounts() {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT Count(*) FROM Account WHERE status = 'AUTHENTICATION_PENDING'");

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public boolean verifyAccount(Account account, String status) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        account.setStatus(status);
        try {
            session.update(account);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String getStatus(Integer accountId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT a.status FROM Account a WHERE id = :accountId");
        query.setParameter("accountId", accountId);
        return (String) query.getSingleResult();
    }

    @Override
    public void updateAccount(Account account) throws Exception{
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        try {
            session.saveOrUpdate(account);
        } catch (HibernateException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Integer countAccounts(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        // Query query = session.createQuery("SELECT Count(*) FROM Account");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        criteriaQuery.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();
        if(params != null){
            String kw = params.get("kw");
            if(kw != null){
                predicates.add(builder.like(root.get("email"), String.format("%%%s%%", kw)));
            }
        }

        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        Query query = session.createQuery(criteriaQuery);

        return Integer.parseInt(query.getSingleResult().toString());

    }
}
