package com.example.JavaMailSender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.JavaMailSender.entity.EmailData;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender)
	{
		this.mailSender = javaMailSender;
	}
	
	public void sendEmail(EmailData emailData) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailData.getEmail());
		message.setSubject("The sender name is : "+ emailData.getName());
		message.setText(emailData.getMessage());
		message.setFrom("ethan.hazari2022@gmail.com");
		mailSender.send(message);
	}
}
