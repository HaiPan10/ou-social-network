package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Account;
import com.ou.pojo.Role;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.AccountRepository;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.RoleService;
import com.ou.service.interfaces.UserService;
import com.ou.service.interfaces.UserStudentService;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserStudentService userStudentService;

    @Override
    public Account retrieve(Integer id) {
        return accountRepository.retrieve(id);
    }

    @Override
    public List<Account> list() {
        return accountRepository.list();
    }

    @Override
    public Account create(Account account) throws Exception {
        if (!account.getPassword().equals(account.getConfirmPassword())) {
            throw new Exception("mật khẩu không khớp!");
        } else {
            try {
                account.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
                return accountRepository.create(account);
            } catch (ConstraintViolationException e) {
                throw new Exception("email này đã được sử dụng");
            }
        }
    }

    // Hàm gọi khi sinh viên gởi yêu cầu tạo tài khoản
    @Override
    public Account createPendingAccount(Account account, User user, UserStudent userStudent) throws Exception {
        try {
            account.setStatus("PENDING");
            create(account);
            userService.create(user, account);
            userStudentService.create(userStudent, user);
            return account;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
}
