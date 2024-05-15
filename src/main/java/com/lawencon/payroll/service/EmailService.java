package com.lawencon.payroll.service;

public interface EmailService {
	void sendEmail(String toEmail, String subject, String body);
}
