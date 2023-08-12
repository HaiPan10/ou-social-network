package com.ou.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ou.configs.JwtService;
import com.ou.pojo.Account;
import com.ou.pojo.AuthRequest;
import com.ou.pojo.AuthResponse;
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

    @Autowired
    private JwtService jwtService;

    @Override
    public Account retrieve(Integer id) throws Exception {
        Optional<Account> accountOptional = accountRepository.retrieve(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new Exception("Account not found");
        }
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
    public AuthResponse create(Account account, User user, UserStudent userStudent) throws Exception {
        try {
            account.setRoleId(roleService.retrieve(1));
            account.setStatus("EMAIL_VERIFICATION_PENDING");
            account.setVerificationCode(RandomString.make(64));
            create(account);
            userService.create(user, account);
            userStudentService.create(userStudent, user);
            user.setUserStudent(userStudent);
            account.setUser(user);
            mailService.sendVerificationEmail(account.getId());
            String token = jwtService.generateAccessToken(account);
            return new AuthResponse(account.getRoleId(), user, token);
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
    public AuthResponse login(AuthRequest account) throws Exception {
        try{
            Optional<Account> accountOptional = accountRepository.findByEmail(account.getEmail());
            if (!accountOptional.isPresent()) {
                throw new Exception("Email không tồn tại!");
            } 
            
            Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                account.getEmail(), account.getPassword())
            );

            Account authenticationAccount = accountOptional.get();

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateAccessToken(authenticationAccount);

            // if (!authenticationAccount.getStatus().equals("ACTIVE")) {
            //     // EXCEPTION JSON FOR CLIENT
            //     String jsonString = new JSONObject()
            //                             .put("id", authenticationAccount.getId())
            //                             .put("status", authenticationAccount.getStatus())
            //                             .put("accessToken", token)
            //                             .toString();
            //     throw new Exception(jsonString);
            // }

            return new AuthResponse(authenticationAccount.getRoleId() ,authenticationAccount.getUser(), token);
        } catch(AuthenticationException exception){
            throw new Exception("Email hoặc mật khẩu không đúng.");
        }
    }

    @Override
    public String getStatus(Integer accountId) {
        return accountRepository.getStatus(accountId);
    }

    @Override
    public Account create(Account account, User user) throws Exception {
        try {
            account.setRoleId(roleService.retrieve(2));
            create(account);
            System.out.println("[DEBUG] - Saved account id: " + account.getId());
            userService.create(user, account);
            account.setUser(user);
            mailService.sendGrantedAccount(account);
            return account;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void changePassword(Account account, String authPassword) throws Exception {
        try {
            Optional<Account> optionalAccount = accountRepository.findByEmail(account.getEmail());
            if(!optionalAccount.isPresent()){
                throw new Exception("Email không tồn tại");
            };
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    account.getEmail(), authPassword)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String encoded = bCryptPasswordEncoder.encode(account.getPassword());
            account.setId(optionalAccount.get().getId());
            account.setPassword(encoded);
            account.setConfirmPassword(encoded);
            accountRepository.updateAccount(account);
        } 
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
