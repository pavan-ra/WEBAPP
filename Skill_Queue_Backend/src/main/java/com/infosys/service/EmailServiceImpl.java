package com.infosys.service;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOtp(String to, String subject, String otp) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@skillqueue.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText("One time password: " + otp);
        javaMailSender.send(message);
    }

}
