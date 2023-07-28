package com.ou.service.interfaces;

public interface MailService {
    void sendEmail(String userEmail, String subject, String content);
    void sendVerificationEmail(Integer accountId) throws Exception;
}
