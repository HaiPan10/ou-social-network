package com.ou.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.PostInvitation;
import com.ou.pojo.PostInvitationUser;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostInvitationRepository;

@Repository
@Transactional
public class PostInvitationRepositoryImpl implements PostInvitationRepository {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public PostInvitation create(PostInvitation postInvitation, List<User> listUsers) {
        System.out.println("[DEBUG] - START TO INSERT POST INVITATION");
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        postInvitation.setId((Integer) session.save(postInvitation));
        if (listUsers != null) {
            listUsers.stream().forEach(u -> {
                PostInvitationUser p = new PostInvitationUser();
                p.setPostInvitationId(postInvitation);
                p.setUserId(u);
                session.save(p);
            });
        }
        return postInvitation;
    }

}
