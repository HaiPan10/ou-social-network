package com.ou.repository.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.ou.pojo.Comment;
import com.ou.pojo.Post;
import com.ou.pojo.PostInvitation;
import com.ou.pojo.PostSurvey;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostRepository;

@Repository
@Transactional(rollbackOn = Exception.class)
public class PostRepositoryImpl implements PostRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Autowired
    private Environment env;

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
    public Optional<List<Post>> loadPost(Integer userId, @RequestParam Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> rPost = criteriaQuery.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(rPost.get("userId"), userId));
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.orderBy(builder.desc(rPost.get("createdAt")));

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
        int pageSize = Integer.parseInt(this.env.getProperty("POST_PAGE_SIZE"));

        query.setMaxResults(pageSize);
        query.setFirstResult((page - 1) * pageSize);

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
            persistPost.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            s.update(persistPost);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Post> retrieve(Integer postId) throws Exception {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        Post persistPost = (Post) s.get(Post.class, postId);
        if (persistPost == null) {
            throw new Exception("Null post");
        }
        PostSurvey persistPostSurvey = persistPost.getPostSurvey();
        if (persistPostSurvey != null) {
            Query query = s.createQuery("FROM Question WHERE surveyId.id = :postId");
            query.setParameter("postId", postId);
            persistPostSurvey.setQuestions(query.getResultList());
        }
        PostInvitation persistPostInvitation = persistPost.getPostInvitation();
        if (persistPostInvitation != null) {
            Query query = s.createQuery("FROM PostInvitationUser WHERE postInvitationId.id = :postId");
            query.setParameter("postId", postId);
            persistPostInvitation.setPostInvitationUsers(query.getResultList());
        }
        try {
            return Optional.ofNullable(persistPost);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // @Override
    // public Optional<Post> retrieve(Integer postId) {
    //     Session s = sessionFactoryBean.getObject().getCurrentSession();
    //     try {
    //         return Optional.ofNullable((Post) s.get(Post.class, postId));
    //     } catch (NoResultException e) {
    //         return Optional.empty();
    //     }
    // }

    // @Override
    // public Optional<Post> retrieve(Integer postId) {
    //     Session session = sessionFactoryBean.getObject().getCurrentSession();
        
    //     CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    //     CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
    //     Root<Post> root = criteriaQuery.from(Post.class);
        
    //     Join<Post, PostSurvey> postSurveyJoin = root.join("postSurvey", JoinType.LEFT);
    
    //     postSurveyJoin.fetch("questions", JoinType.LEFT);
        
    //     criteriaQuery
    //         .select(root)
    //         .where(criteriaBuilder.equal(root.get("id"), postId));
            
    //     try {
    //         Post post = session.createQuery(criteriaQuery).getSingleResult();
    //         return Optional.ofNullable(post);
    //     } catch (NoResultException e) {
    //         return Optional.empty();
    //     }
    // }

    @Override
    public boolean delete(Post persistPost) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        System.out.println("DELETE POST: " + persistPost);
        try {
            s.delete(persistPost);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<List<Post>> loadNewFeed(@RequestParam Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> rPost = criteriaQuery.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();

        Subquery<Date> subquery = criteriaQuery.subquery(Date.class);
        Root<Comment> rComment = subquery.from(Comment.class);
        List<Predicate> subPredicates = new ArrayList<>();
        subPredicates.add(builder.equal(rComment.get("postId"), rPost.get("id")));
        subquery.where(subPredicates.toArray(Predicate[]::new));
        subquery.select((Expression) builder.max(rComment.get("updatedDate")));

        criteriaQuery.orderBy(
                builder.desc(
                        builder.coalesce(
                                subquery,
                                rPost.get("createdAt"))),
                builder.desc(rPost.get("createdAt")));

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
        int pageSize = Integer.parseInt(this.env.getProperty("POST_PAGE_SIZE"));

        query.setMaxResults(pageSize);
        query.setFirstResult((page - 1) * pageSize);

        try {
            List<Post> posts = query.getResultList();
            return Optional.of(posts);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Post> list(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);

        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
        criteriaQuery.orderBy(builder.desc(root.get("createdAt")));

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
        int pageSize = Integer.parseInt(this.env.getProperty("POST_PAGE_SIZE"));

        query.setMaxResults(pageSize);
        query.setFirstResult((page - 1) * pageSize);
        return query.getResultList();
    }

    @Override
    public Integer countPosts(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        // Query query = session.createQuery("SELECT Count(*) FROM Post");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(builder.count(root));
        Join<Post, User> join = root.join("userId");

        Predicate finalPredicate = null;

        if (params != null) {

            String kw = params.get("kw");
            if (kw == null || kw.isEmpty()) {
                kw = "_";
            } else {
                kw = kw.trim();
            }

            Expression<String> expression = builder.function("CONCAT", String.class, join.get("id"),
                    join.get("lastName"), builder.literal(" "), join.get("firstName"));

            finalPredicate = builder.like(builder.lower(expression), String.format("%%%s%%", kw.toLowerCase()));

        }

        criteriaQuery.where(finalPredicate);
        Query query = session.createQuery(criteriaQuery);

        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public List<Post> search(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        Join<Post, User> join = root.join("userId");

        criteriaQuery.select(root);

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

            Expression<String> expression = builder.function("CONCAT", String.class, join.get("id"),
                    join.get("lastName"), builder.literal(" "), join.get("firstName"));

            finalPredicate = builder.like(builder.lower(expression), String.format("%%%s%%", kw.toLowerCase()));

        } else {
            page = 1;
        }
        int pageSize = Integer.parseInt(this.env.getProperty("POST_PAGE_SIZE"));

        criteriaQuery.where(finalPredicate);
        Query query = session.createQuery(criteriaQuery);

        query.setMaxResults(pageSize);
        query.setFirstResult((page - 1) * pageSize);
        return query.getResultList();
    }
}
