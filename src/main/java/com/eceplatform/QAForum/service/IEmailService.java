package com.eceplatform.QAForum.service;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {

	void sendEmail(SimpleMailMessage mail);

}
