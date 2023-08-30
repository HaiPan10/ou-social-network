package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.PostInvitation;
import com.ou.pojo.User;

public interface PostInvitationService {
    PostInvitation create(Integer postId, PostInvitation postInvitation, List<User> listUsers);
}
