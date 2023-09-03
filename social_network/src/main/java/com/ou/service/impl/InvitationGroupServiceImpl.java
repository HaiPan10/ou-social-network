package com.ou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.InvitationGroup;
import com.ou.repository.interfaces.InvitationGroupRepository;
import com.ou.service.interfaces.InvitationGroupService;

@Service
public class InvitationGroupServiceImpl implements InvitationGroupService {
    @Autowired
    private InvitationGroupRepository invitationGroupRepository;

    @Override
    public List<InvitationGroup> list() {
        return invitationGroupRepository.list();
    }
    
}
