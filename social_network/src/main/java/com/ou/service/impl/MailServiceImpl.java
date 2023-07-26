package com.ou.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.ou.service.interfaces.MailService;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    MailSender mailSender;

    @Override
    public void sendEmail(String userEmail, String subject, String content) {
        // SimpleMailMessage mailMessage = new SimpleMailMessage();
        // mailMessage.setFrom("admin@ousocialnetwork.id.vn");
        // mailMessage.setTo(userEmail);
        // mailMessage.setSubject(subject);
        // mailMessage.setText(content);

        // mailSender.send(mailMessage);

        Session session = Session.getDefaultInstance(((JavaMailSenderImpl) mailSender).getJavaMailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(((JavaMailSenderImpl) mailSender).getUsername(), ((JavaMailSenderImpl) mailSender).getPassword());
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(((JavaMailSenderImpl) mailSender).getUsername(), "OU Social Network"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
