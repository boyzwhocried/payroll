package com.lawencon.payroll.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
