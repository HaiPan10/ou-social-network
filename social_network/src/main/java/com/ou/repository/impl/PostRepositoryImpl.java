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
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.ou.pojo.Post;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostRepository;

@Repository
@Transactional
public class PostRepositoryImpl implements PostRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Post uploadPost(Post post, Integer userId) throws Exception {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        User persistUser = s.get(User.class, userId);
        if (persistUser == null) {
            throw new Exception("Không tìm thấy người dùng!");
        }
        post.setUserId(persistUser);
        post.setId((Integer) s.save(post));
        return post;
    }

    @Override
    public Optional<List<Post>> loadPost(Integer userId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
        
        Root<Post> rPost = criteriaQuery.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(rPost.get("userId"), userId));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.orderBy(builder.desc(rPost.get("createdAt")));

        Query query = session.createQuery(criteriaQuery);
        try {
            List<Post> posts = query.getResultList();
            return Optional.of(posts);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Post persistPost, Post post) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        try {
            if (post.getContent() != null) {
                persistPost.setContent(post.getContent());
            }
            if (post.getIsActiveComment() != null) {
                persistPost.setIsActiveComment(post.getIsActiveComment());
            }
            s.update(persistPost);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Post> retrieve(Integer postId) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        try {
            return Optional.ofNullable((Post) s.get(Post.class, postId));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
