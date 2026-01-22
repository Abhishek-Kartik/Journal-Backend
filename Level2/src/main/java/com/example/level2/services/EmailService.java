package com.example.level2.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail){

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Test Email from Journal App");
            message.setText(
                    "Hello This is a test email sent using SMTP from Journal App"+
                            "If you received this, SMTP is working.."
            );
            message.setFrom("pointsfocus5@gmail.com");
            message.setTo(toEmail);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Unsuccessful sent",e);
        }

    }
}
