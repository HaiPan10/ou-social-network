package com.ou.repository.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.ou.pojo.Post;
import com.ou.pojo.PostReaction;
import com.ou.pojo.Reaction;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostReactionRepository;
import com.ou.repository.interfaces.PostRepository;
import com.ou.repository.interfaces.UserRepository;

@Repository
@Transactional
public class PostReactionRepositoryImpl implements PostReactionRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Autowired
    private PostRepository postRepository;

    @Autowired 
    private UserRepository userRepository;

    @Override
    public List<PostReaction> countReaction(Integer postId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM PostReaction WHERE postId.id = :postId");
        query.setParameter("postId", postId);
        return query.getResultList();
    }
    
    @Override
    public Optional<PostReaction> retrieve(Integer postReactionId) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        try {
            return Optional.ofNullable((PostReaction) s.get(PostReaction.class, postReactionId));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public PostReaction saveOrUpdate(Integer postId, Integer userId, Reaction reaction) throws Exception {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        Query query = s.createQuery("FROM PostReaction WHERE userId.id = :userId AND postId.id = :postId");
        query.setParameter("userId", userId);
        query.setParameter("postId", postId);
        Reaction persistReaction = s.get(Reaction.class, reaction.getId());
        try {
            PostReaction persistPostReaction = (PostReaction) query.getSingleResult();
            persistPostReaction.setReactionId(persistReaction);
            persistPostReaction.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            s.update(persistPostReaction);
            return persistPostReaction;
        } catch (NoResultException e) {
            PostReaction postReaction = new PostReaction();
            Optional<Post> optionalPost = postRepository.retrieve(postId);
            if (optionalPost.isPresent()) {
                postReaction.setPostId(optionalPost.get());
            } else {
                throw new Exception("Post is unavailable!");
            }
            Optional<User> optionalUser = userRepository.retrieve(userId);
            if (optionalUser.isPresent()) {
                postReaction.setUserId(optionalUser.get());
            } else {
                throw new Exception("User is unavailable!");
            }
            postReaction.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            postReaction.setReactionId(persistReaction);
            s.save(postReaction);
            return postReaction;
        }
    }

    @Override
    public boolean delete(Integer postId, Integer userId) throws Exception {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        Query query = s.createQuery("FROM PostReaction WHERE userId.id = :userId AND postId.id = :postId");
        query.setParameter("userId", userId);
        query.setParameter("postId", postId);
        try {
            PostReaction persistPostReaction = (PostReaction) query.getSingleResult();
            s.delete(persistPostReaction);
            return true;
        } catch (NoResultException e) {
            throw new Exception("Post reaction not found!");
        }
    }

    // Pagination this later
    @Override
    public List<User> getReactionUsers(Integer postId, Integer reactionId) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        Query query = s.createQuery("SELECT p.userId FROM PostReaction p WHERE p.reactionId.id = :reactionId AND p.postId.id = :postId");
        query.setParameter("reactionId", reactionId);
        query.setParameter("postId", postId);
        return query.getResultList();
    }
}
