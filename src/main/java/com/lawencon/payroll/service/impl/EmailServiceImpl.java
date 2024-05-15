package com.lawencon.payroll.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("josepgultoem@gmail.com");
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);

        emailSender.send(message);
		
		System.out.println("Mail Sent Successfully...");
	}
}
