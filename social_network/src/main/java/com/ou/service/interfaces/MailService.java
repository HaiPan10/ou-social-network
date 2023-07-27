package com.ou.service.interfaces;

import com.ou.pojo.Account;

public interface MailService {
    void sendEmail(String userEmail, String subject, String content);
    void sendVerificationEmail(Account account) throws Exception;
}
