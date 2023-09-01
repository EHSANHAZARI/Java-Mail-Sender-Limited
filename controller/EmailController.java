	package com.example.JavaMailSender.controller;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RestController;

	import com.example.JavaMailSender.entity.EmailData;
	import com.example.JavaMailSender.service.EmailService;

	@RestController
	public class EmailController {
	    private final EmailService emailService;

	    @Autowired
	    public EmailController(EmailService emailService) {
	        this.emailService = emailService;
	    }

	    @PostMapping("/email")
	    public void sendEmail(@RequestBody EmailData emailData) {
	        emailService.sendEmail(emailData);
	    }
	}
