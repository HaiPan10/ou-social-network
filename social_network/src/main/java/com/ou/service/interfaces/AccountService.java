package com.ou.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;

public interface AccountService{
    Account retrieve(Integer id);
    List<Account> list();
    Account create(Account account) throws Exception;
    Account createPendingAccount(Account account, User user, UserStudent userStudent) throws Exception;
    List<Account> getPendingAccounts(Map<String, String> params);
    Integer countPendingAccounts();
    boolean verifyAccount(Account account, String status);
    public boolean login(Account account) throws AccountNotFoundException;
}
