package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;

public interface GroupRepository {
    InvitationGroup create(InvitationGroup invitationGroup);
    void addUsers(Integer groupId, List<User> users) throws Exception;
    List<Object[]> getUsers(Integer groupId);
}
