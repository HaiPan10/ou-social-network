package com.ou.repository.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
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
import com.ou.pojo.PostInvitationUser;
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
    public Optional<List<Post>> loadPost(Integer userId, @RequestParam Map<String, String> params, Integer currentUserId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> rPost = criteriaQuery.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();
        rPost.fetch("postSurvey", JoinType.LEFT).fetch("questions", JoinType.LEFT);

        Join<Post, PostInvitation> joinPostInvitation = rPost.join("postInvitation", JoinType.LEFT);
        Join<PostInvitation, PostInvitationUser> joinPostInvitationUser = joinPostInvitation.join("postInvitationUsers", JoinType.LEFT);

        predicates.add(builder.or(
            builder.isNull(joinPostInvitation),
            builder.isNull(joinPostInvitationUser),
            builder.and(
                builder.equal(joinPostInvitationUser.get("userId"), currentUserId)
                // builder.greaterThan(joinPostInvitation.get("startAt"), new Date())
            )
        ));

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
            posts.forEach(post -> {
                try {
                    Hibernate.initialize(post.getPostInvitation().getPostInvitationUsers());
                } catch (NullPointerException e) {

                }
            });
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
    public Optional<List<Post>> loadNewFeed(Integer currentUserId, @RequestParam Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);

        Root<Post> rPost = criteriaQuery.from(Post.class);
        List<Predicate> predicates = new ArrayList<>();

        rPost.fetch("postSurvey", JoinType.LEFT).fetch("questions", JoinType.LEFT);
        Join<Post, PostInvitation> joinPostInvitation = rPost.join("postInvitation", JoinType.LEFT);
        Join<PostInvitation, PostInvitationUser> joinPostInvitationUser = joinPostInvitation.join("postInvitationUsers", JoinType.LEFT);

        System.out.println("startAt" + joinPostInvitation.get("startAt"));
        System.out.println("TODAY " + new Date());
        predicates.add(builder.or(
            builder.isNull(joinPostInvitation),
            builder.isNull(joinPostInvitationUser),
            builder.and(
                builder.equal(joinPostInvitationUser.get("userId"), currentUserId)
                // builder.greaterThan(joinPostInvitation.get("startAt"), new Date())
            )
        ));

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
            posts.forEach(post -> {
                try {
                    Hibernate.initialize(post.getPostInvitation().getPostInvitationUsers());
                } catch (NullPointerException e) {

                }
            });
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

    @Override
    public List<Object[]> stat(Map<String, String> params) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<Post> root = criteriaQuery.from(Post.class);
        Join<Post, PostSurvey> joinSurvey = root.join("postSurvey", JoinType.LEFT);
        Join<Post, PostInvitation> joinInvitation = root.join("postInvitation", JoinType.LEFT);

        Expression<Integer> expression = null;

        String month = params.get("byMonth");
        String quarter = params.get("byQuarter");
        if (month != null && month.equals("true")) {
            expression = builder.function("MONTH", Integer.class, root.get("createdAt"));
        } else if (quarter != null && quarter.equals("true")) {
            expression = builder.function("QUARTER", Integer.class, root.get("createdAt"));
        } else {
            expression = builder.function("YEAR", Integer.class, root.get("createdAt"));
        }

        List<Predicate> predicates = new ArrayList<>();

        String year = params.get("year");
        if (year != null) {
            predicates.add(builder.equal(builder.function("YEAR", Integer.class, root.get("createdAt")),
                    Integer.valueOf(year)));
        }

        predicates.add(builder.isNull(joinSurvey.get("id")));
        predicates.add(builder.isNull(joinInvitation.get("id")));

        criteriaQuery.multiselect(expression, builder.count(root.get("id")))
                .where(predicates.toArray(Predicate[]::new)).groupBy(expression);

        Query query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
