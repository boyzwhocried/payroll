package com.lawencon.payroll.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.service.EmailService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    
    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        final SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("josepgultoem@gmail.com");
        message.setTo(to); 
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

}
