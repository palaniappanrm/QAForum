package com.eceplatform.QAForum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eceplatform.QAForum.service.IEmailService;

@Service
public class EmailService implements IEmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Async
	@Override
	public void sendEmail(SimpleMailMessage mail) {
		javaMailSender.send(mail);
	}
}
