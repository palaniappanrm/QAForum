package com.eceplatform.QAForum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class QaForumApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(QaForumApplication.class, args);
	}
}
