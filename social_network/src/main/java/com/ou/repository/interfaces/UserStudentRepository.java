package com.ou.repository.interfaces;

import java.util.Optional;

import com.ou.pojo.UserStudent;

public interface UserStudentRepository {
    UserStudent create(UserStudent userStudent);
    Optional<UserStudent> findByStudentIdentical(String studentIdentical);
}
