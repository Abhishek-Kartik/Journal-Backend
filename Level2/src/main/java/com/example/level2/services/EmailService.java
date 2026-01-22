package com.example.level2.services;

//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Test Email from Journal App");
        message.setText(
                "Hello This is a test email sent using SMTP from Journal App"+
                        "If you received this, SMTP is working.."
        );
        message.setFrom("pointsfocus5@gmail.com");
        message.setTo(toEmail);
        mailSender.send(message);
    }
}
//
//try {
//MimeMessage message = mailSender.createMimeMessage();
//MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
//
//            helper.setTo(toEmail);
//            helper.setSubject("Journal Reminder");
//            helper.setText("This is a test email from Journal App.", false);
//
//            helper.setFrom(new InternetAddress(
//                                   "pointsfocus5@gmail.com",
//                    "Journal App"
//));
//
//        mailSender.send(message);
//
//        } catch (Exception e) {
//        throw new RuntimeException("Failed to send email", e);
//        }
