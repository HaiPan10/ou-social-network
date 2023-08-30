package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.InvitationGroup;
import com.ou.pojo.User;

public interface GroupRepository {
    InvitationGroup create(InvitationGroup invitationGroup);
    void addUsers(List<User> users) throws Exception;
}
