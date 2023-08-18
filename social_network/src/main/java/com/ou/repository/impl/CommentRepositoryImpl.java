package com.ou.repository.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Comment;
import com.ou.pojo.Post;
import com.ou.pojo.User;
import com.ou.repository.interfaces.CommentRepository;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {
@Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public Integer countComment(Integer postId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("SELECT Count(*) FROM Comment WHERE postId.id = :postId");
        query.setParameter("postId", postId);
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public Comment create(Comment comment, Post persistPost, User persistUser) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        comment.setPostId(persistPost);
        comment.setUserId(persistUser);
        comment.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        comment.setUpdatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        comment.setId((Integer) session.save(comment));
        return comment;
    }

    @Override
    public List<Comment> loadComment(Integer postId) {
        // Session session = sessionFactoryBean.getObject().getCurrentSession();
        // Query query = session.createQuery("SELECT p.commentList FROM Post p WHERE p.id = :postId");
        // query.setParameter("postId", postId);
        // return query.getResultList();
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = builder.createQuery(Comment.class);
        
        Root<Comment> rComment = criteriaQuery.from(Comment.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(rComment.get("postId"), postId));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.orderBy(builder.desc(rComment.get("createdDate")));

        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> retrieve(Integer commentId) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        try {
            return Optional.ofNullable((Comment) s.get(Comment.class, commentId));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Comment editComment(Comment persistComment, Comment comment) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        persistComment.setContent(comment.getContent());
        persistComment.setUpdatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        s.update(persistComment);
        return persistComment;
    }

    @Override
    public boolean delete(Comment persistComment) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        try {
            s.delete(persistComment);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
