package com.ou.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.ou.pojo.PostInvitation;
import com.ou.pojo.User;

public interface PostInvitationRepository {
    PostInvitation create(PostInvitation postInvitation, List<User> listUsers);
    List<Object[]> stat(Map<String, String> params);
}
