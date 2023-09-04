package com.infosys.service;


import jakarta.mail.MessageRemovedException;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendOtp(String from, String subject, String otp) throws MessagingException;
}
