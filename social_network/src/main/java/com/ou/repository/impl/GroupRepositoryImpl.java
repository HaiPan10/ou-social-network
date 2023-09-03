package com.ou.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Account;
import com.ou.pojo.GroupUser;
import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;
import com.ou.repository.interfaces.GroupRepository;

@Repository
@Transactional
public class GroupRepositoryImpl implements GroupRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public InvitationGroup create(InvitationGroup invitationGroup) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        invitationGroup.setId((Integer) session.save(invitationGroup));
        return invitationGroup;
    }

    @Override
    public void addUsers(Integer groupId, List<User> users) throws Exception {
        Session session = sessionFactoryBean.getObject().getCurrentSession();
        InvitationGroup group = session.get(InvitationGroup.class, groupId);
        List<GroupUser> groupList = Optional.ofNullable(group.getGroupUsers()).orElse(new ArrayList<GroupUser>());
        users.forEach(u -> {
            GroupUser groupUser = new GroupUser();
            groupUser.setUserId(u);
            groupUser.setGroupId(group);
            groupList.add(groupUser);
        });
        group.setGroupUsers(groupList);
        session.saveOrUpdate(group);
    }

    @Override
    public List<Object[]> getUsers(Integer groupId) {
        Session session = sessionFactoryBean.getObject().getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
        Root<InvitationGroup> root = criteriaQuery.from(InvitationGroup.class);
        Join<InvitationGroup, GroupUser> joinGroupUser = root.join("groupUsers");
        Join<GroupUser, User> joinUser = joinGroupUser.join("userId");
        Join<User, Account> joinAccount = joinUser.join("account");

        criteriaQuery.where(builder.equal(root.get("id"), groupId));

        criteriaQuery.multiselect(joinUser.get("id"), joinAccount.get("email"), builder.function("concat", String.class,
                joinUser.get("lastName"), builder.literal(" "), joinUser.get("firstName")), joinUser.get("avatar"));
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
}
