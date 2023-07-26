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
import com.ou.service.interfaces.MailService;
import com.ou.service.interfaces.RoleService;
import com.ou.service.interfaces.UserService;
import com.ou.service.interfaces.UserStudentService;

import net.bytebuddy.utility.RandomString;

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
    private MailService mailService;

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
            account.setStatus("EMAIL_VERIFICATION_PENDING");
            account.setVerificationCode(RandomString.make(64));
            create(account);
            userService.create(user, account);
            userStudentService.create(userStudent, user);
            user.setUserStudent(userStudent);
            account.setUser(user);
            String mailBody = String.format("Xin chào %s,<br>"
            + "Cảm ơn bạn đã đăng kí tài khoản mạng xã hội cựu sinh viên trường đại học Mở TP.HCM<br>"
            + "Vui lòng nhấn vào đường link bên dưới để xác thực<br>"
            + "<h3><a href=\"%s\">XÁC THỰC NGAY</a></h3>"
            + "Chúng tôi xin cảm ơn,<br>"
            + "OU Social Network", user.getFirstName(), 
            String.format("http://localhost:8080/social_network/api/accounts/verify/%d/%s", account.getId(), account.getVerificationCode()));
            mailService.sendEmail(account.getEmail(), "Xác thực email", mailBody);
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

    @Override
    public boolean verifyEmail(Integer accountId, String verificationCode) throws Exception {
        Account account = retrieve(accountId);
        if (account.getVerificationCode().equals(verificationCode)) {
            return verifyAccount(account, "AUTHENTICATION_PENDING");
        } else {
            throw new Exception("Verification code doesn't match!");
        }
    }
}
