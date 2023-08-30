package com.ou.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.User;
import com.ou.repository.interfaces.UserRepository;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public User create(User user) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        user.setId(user.getAccount().getId());
        s.save(user);
        return user;
    }

    @Override
    public User updateAvatar(User persistUser, String url) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        persistUser.setAvatar(url);
        s.saveOrUpdate(persistUser);
        return persistUser;
    }

    @Override
    public User updateCover(User persistUser, String url) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        persistUser.setCoverAvatar(url);
        s.saveOrUpdate(persistUser);
        return persistUser;
    }

    @Override
    public Optional<User> retrieve(Integer id) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        try {
            return Optional.ofNullable((User) s.get(User.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Object updateUser(User user, Integer userId) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        User persistUser = s.get(User.class, userId);
        if (user.getDob() != null) {
            persistUser.setDob(user.getDob());
        }        
        s.update(persistUser);
        return persistUser;
    }

    @Override
    public List<User> list(List<Integer> listUserId) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root);
        criteriaQuery.where(root.get("id").in(listUserId));
        
        Query query = s.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
}
