package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;

public interface GroupService {
    InvitationGroup create(InvitationGroup group);
    void addUsers(List<User> users) throws Exception;
}
