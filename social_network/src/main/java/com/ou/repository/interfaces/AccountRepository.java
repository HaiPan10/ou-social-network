package com.ou.repository.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ou.pojo.Account;

public interface AccountRepository {
    Optional<Account> retrieve(Integer id);
    List<Account> list(Map<String, String> params);
    Account create(Account account);
    Optional<Account> findByEmail(String email);
    List<Account> getPendingAccounts(Map<String, String> params);
    Integer countPendingAccounts();
    boolean verifyAccount(Account account, String status);
    String getStatus(Integer accountId);
    void updateAccount(Account account) throws Exception; 
    Integer countAccounts();
}
