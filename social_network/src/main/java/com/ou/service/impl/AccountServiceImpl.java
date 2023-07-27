package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.pojo.Account;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.repository.interfaces.AccountRepository;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.MailService;
import com.ou.service.interfaces.RoleService;
import com.ou.service.interfaces.UserService;
import com.ou.service.interfaces.UserStudentService;

import net.bytebuddy.utility.RandomString;

@Service("accountDetailService")
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserStudentService userStudentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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
            account.setStatus("EMAIL_VERIFICATION_PENDING");
            account.setVerificationCode(RandomString.make(64));
            create(account);
            userService.create(user, account);
            userStudentService.create(userStudent, user);
            user.setUserStudent(userStudent);
            account.setUser(user);
            mailService.sendVerificationEmail(account);
            return account;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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

    @Override
    public boolean verifyEmail(Integer accountId, String verificationCode) throws Exception {
        Account account = retrieve(accountId);
        if (!account.getStatus().equals("EMAIL_VERIFICATION_PENDING")) {
            throw new Exception("This account can't not be verified");
        }
        if (account.getVerificationCode().equals(verificationCode)) {
            return verifyAccount(account, "AUTHENTICATION_PENDING");
        } else {
            throw new Exception("Verification code doesn't match!");
        }
    }

    @Override
    public boolean login(Account account) throws Exception {
        try{
            Optional<Account> accountOptional = accountRepository.findByEmail(account.getEmail());
            if (!accountOptional.isPresent()) {
                throw new Exception("Email không tồn tại!");
            } else {
                Account retrieveAccount = accountOptional.get();
                if (!retrieveAccount.getStatus().equals("ACTIVE")) {
                    // EXCEPTION FORMAT ACCOUNT.{STATUS} FOR CLIENT
                    throw new Exception(String.format("ACCOUNT.%s", retrieveAccount.getStatus()));
                }
            }
            Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                account.getEmail(), account.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch(AuthenticationException exception){
            throw new Exception("Sai mật khẩu!");
        }
    }

}
