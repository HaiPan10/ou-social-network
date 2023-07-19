package com.ou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.Account;
import com.ou.repository.interfaces.AccountRepository;
import com.ou.service.interfaces.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.getAccountById(id);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.getAccounts();
    }
    
}
