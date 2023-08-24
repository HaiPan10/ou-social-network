package com.ou.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import com.ou.pojo.Account;
import com.ou.pojo.AuthRequest;
import com.ou.pojo.AuthResponse;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;

public interface AccountService{
    Account retrieve(Integer id) throws Exception;
    List<Account> list(Map<String, String> params);
    Account create(Account account) throws Exception;
    AuthResponse create(Account account, User user, UserStudent userStudent) throws Exception;
    List<Account> getPendingAccounts(Map<String, String> params);
    Integer countPendingAccounts();
    boolean verifyAccount(Account account, String status);
    boolean verifyEmail(Integer accountId, String verificationCode) throws Exception;
    AuthResponse login(AuthRequest account) throws AccountNotFoundException, Exception;
    String getStatus(Integer accountId);
    Account create(Account account, User user) throws Exception;
    void changePassword(String changedPassword, String authPassword)throws Exception;
    Account loadAccountByEmail(String email);
    Integer countAccounts();
}
