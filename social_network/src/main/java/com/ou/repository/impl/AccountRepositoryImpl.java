package com.ou.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
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
import com.ou.pojo.User;
import com.ou.repository.interfaces.AccountRepository;

@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {
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
    public List<Account> search(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        System.out.println("[DEBUG] - START FILTER");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root);
        Join<Account, User> join = root.join("user");

        // List<Predicate> predicates = new ArrayList<>();
        Predicate finalPredicate = null;

        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);
            } else {
                page = 1;
            }

            String kw = params.get("kw");
            if (kw == null || kw.isEmpty()) {
                kw = "_";
            } else {
                kw = kw.trim();
            }

            Expression<String> expression = builder.function("CONCAT", String.class, root.get("id"), root.get("email"),
                    join.get("lastName"), builder.literal(" "), join.get("firstName"));

            finalPredicate = builder.like(builder.lower(expression), String.format("%%%s%%", kw.toLowerCase()));

            String status = params.get("status");
            if (status != null) {
                finalPredicate = builder.and(finalPredicate, builder.equal(root.get("status"), status));
            }

        } else {
            page = 1;
        }
        int pageSize = Integer.parseInt(this.env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));

        criteriaQuery.where(finalPredicate);

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
    public void updateAccount(Account account) throws Exception {
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
        Join<Account, User> join = root.join("user");

        // List<Predicate> predicates = new ArrayList<>();
        Predicate finalPredicate = null;

        if (params != null) {
            String kw = params.get("kw");
            if (kw == null || kw.isEmpty()) {
                kw = "_";
            }

            Expression<String> expression = builder.function("CONCAT", String.class, root.get("id"), root.get("email"),
                    join.get("lastName"), builder.literal(" "), join.get("firstName"));

            finalPredicate = builder.like(builder.lower(expression), String.format("%%%s%%", kw.toLowerCase()));

            String status = params.get("status");
            if (status != null) {
                finalPredicate = builder.and(finalPredicate, builder.equal(root.get("status"), status));
            }
        }

        criteriaQuery.where(finalPredicate);
        Query query = session.createQuery(criteriaQuery);

        return Integer.parseInt(query.getSingleResult().toString());

    }

    @Override
    public List<Object[]> list() {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<Account> root = criteriaQuery.from(Account.class);
        Join<Account, User> join = root.join("user");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("status"), "ACTIVE"));
        predicates.add(builder.equal(root.get("status"), "PASSWORD_CHANGE_REQUIRED"));
        criteriaQuery.where(builder.or(predicates.toArray(Predicate[]::new)));

        criteriaQuery.multiselect(root.get("id"), root.get("email"), builder.function("concat", String.class,
                join.get("lastName"), builder.literal(" "), join.get("firstName")), join.get("avatar"));
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
