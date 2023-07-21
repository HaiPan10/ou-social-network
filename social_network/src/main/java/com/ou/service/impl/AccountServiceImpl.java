package com.ou.service.impl;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public Account create(Account account) throws Exception {
        try {
            System.out.println("CREATE");
            return accountRepository.create(account);
        } catch (Exception e) {
            System.out.println("CATCH");
            throw new Exception("Catch");
        }
    }
    
}
