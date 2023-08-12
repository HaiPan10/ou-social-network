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

import com.ou.pojo.Account;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.MailService;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    MailSender mailSender;

    @Autowired
    AccountService accountService;

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

    @Override
    public void sendVerificationEmail(Integer accountId) throws Exception {
        Account account = accountService.retrieve(accountId);
        if (account == null) {
            throw new Exception("Không tìm thấy tài khoản!");
        } else if (!account.getStatus().equals("EMAIL_VERIFICATION_PENDING")) {
            throw new Exception("Trạng thái không hợp lệ!");
        } else {
            String mailBody = String.format("Xin chào %s,<br>"
            + "Cảm ơn bạn đã đăng kí tài khoản mạng xã hội cựu sinh viên trường đại học Mở TP.HCM<br>"
            + "Vui lòng nhấn vào đường link bên dưới để xác thực<br>"
            + "<h3><a href=\"%s\">XÁC THỰC NGAY</a></h3>"
            + "Chúng tôi xin cảm ơn,<br>"
            + "OU Social Network", account.getUser().getFirstName(), 
            String.format("http://localhost:8080/social_network/api/accounts/verify/%d/%s", account.getId(), account.getVerificationCode()));
            sendEmail(account.getEmail(), "Xác thực email", mailBody);
        }
    }

    @Override
    public void sendGrantedAccount(Account account) throws Exception {
        if(account == null){
            throw new Exception("Tài khoản không tồn tại!"); 
        } else {
            String mailBody = String.format("Kính gửi thầy/cô %s,<br>"
            + "<p>Tài khoản mạng mạng xã hội cựu sinh viên trường đại học Mở TP.HCM của thầy/cô đã được kích hoạt</p>"
            + "Thông tin tài khoản bao gồm:<br>"
            + "Email: %s<br>"
            + "Password: ou@123<br>"
            + "Xin quý thầy cô hãy đổi password trong vòng 24h hoặc tài khoản sẽ bị khóa.<br>"
            + "Chúng tôi xin cảm ơn sự quan tâm của quý thầy cô,<br>"
            + "OU Social Network",
                String.format("%s %s",account.getUser().getLastName(),account.getUser().getFirstName()),
                account.getEmail()
            );
            sendEmail(account.getEmail(), "Thông tin cấp tài khoản", mailBody);
        }
    }
}