package com.lawencon.payroll.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

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
