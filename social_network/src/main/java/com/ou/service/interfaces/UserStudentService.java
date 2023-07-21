package com.ou.service.interfaces;

import com.ou.pojo.User;
import com.ou.pojo.UserStudent;

public interface UserStudentService {
    UserStudent create(UserStudent userStudent, User user) throws Exception;
}
