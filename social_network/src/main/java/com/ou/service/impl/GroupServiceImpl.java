package com.ou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.GroupUser;
import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;
import com.ou.repository.interfaces.GroupRepository;
import com.ou.service.interfaces.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService{
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public InvitationGroup create(InvitationGroup group) {
        return groupRepository.create(group);
    }

    @Override
    public void addUsers(Integer groupId, List<User> users) throws Exception {
        groupRepository.addUsers(groupId, users);
    }

    @Override
    public List<Object[]> getUsers(Integer groupId) {
        return groupRepository.getUsers(groupId);
    }
    
}
