package com.ou.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.ou.pojo.Account;

public interface AccountRepository {
    Account retrieve(Integer id);
    List<Account> list();
    Account create(Account account);
    Optional<Account> findByEmail(String email);
}
