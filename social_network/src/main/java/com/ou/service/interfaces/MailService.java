package com.ou.service.interfaces;

import java.util.List;

import com.ou.pojo.Account;

public interface MailService {
    void sendEmail(String userEmail, String subject, String content);
    void sendVerificationEmail(Integer accountId) throws Exception;
    void sendGrantedAccount(Account account) throws Exception;
    void sendAcceptedMail(Account account) throws Exception;
    void sendRejectMail(Account account) throws Exception;
    void sendLockMail(Account account) throws Exception;
    void sendUnlockMail(Account account) throws Exception;
    void sendResetPasswordRequire(Account account) throws Exception;
    void sendEventMail(List<String> email, String body, String title) throws Exception;
}
