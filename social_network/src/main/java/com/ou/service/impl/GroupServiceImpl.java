package com.ou.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;
import com.ou.service.interfaces.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService{

    @Override
    public InvitationGroup create(InvitationGroup group) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void addUsers(List<User> users) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUsers'");
    }
    
}
