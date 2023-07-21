package com.ou.repository.interfaces;

import java.util.List;

import com.ou.pojo.Account;

public interface AccountRepository {
    Account getAccountById(Long id);
    List<Account> getAccounts();
    Account create(Account account) throws Exception;
}
