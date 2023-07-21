package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.Account;

public interface AccountService {
    Account getAccountById(Long id);
    List<Account> getAccounts();
    Account create(Account account);
}
