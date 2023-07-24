package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.AccountRepository;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.RoleService;
import com.ou.service.interfaces.UserService;
import com.ou.service.interfaces.UserStudentService;

@Service("accountDetailService")
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserStudentService userStudentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new Exception("Email này đã được sử dụng!");
        }
        account.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        String encoded = bCryptPasswordEncoder.encode(account.getPassword());
        account.setPassword(encoded);
        account.setConfirmPassword(encoded);
        return accountRepository.create(account);
    }

    // Hàm gọi khi sinh viên gởi yêu cầu tạo tài khoản
    @Override
    public Account createPendingAccount(Account account, User user, UserStudent userStudent) throws Exception {
        try {
            account.setRoleId(roleService.retrieve(1));
            account.setStatus("PENDING");
            create(account);
            userService.create(user, account);
            userStudentService.create(userStudent, user);
            user.setUserStudent(userStudent);
            account.setUser(user);
            return account;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).get();
        if(account == null) {
            throw new UsernameNotFoundException("Email không tồn tại");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(account.getRoleId().getName()));
        return new org.springframework.security.core.userdetails.User(
            account.getEmail(), account.getPassword(), authorities
        );
    }

    @Override
    public List<Account> getPendingAccounts(Map<String, String> params) {
        return accountRepository.getPendingAccounts(params);
    }

    @Override
    public Integer countPendingAccounts() {
        return accountRepository.countPendingAccounts();
    }

    @Override
    public boolean verifyAccount(Account account, String status) {
        return accountRepository.verifyAccount(account, status);
    }
    
}
