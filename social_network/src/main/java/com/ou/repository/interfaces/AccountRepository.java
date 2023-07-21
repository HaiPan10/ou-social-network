package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.Account;

public interface AccountRepository {
    Account retrieve(Long id);
    List<Account> list();
    Account create(Account account);
}