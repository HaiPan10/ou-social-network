package com.ou.repository.interfaces;

import com.ou.pojo.Account;

public interface AccountRepository {
    Account getAccountById(Long id);
}
