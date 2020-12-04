package com.eceplatform.QAForum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QaForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaForumApplication.class, args);
    }
}

/**
 *
 * Threadlocal for logged in user details
 *
 * Get AWS access for s3 image upload
 *
 * Integrate elastic search to put questions and answers into there for word or
 * sentence wise search,
 *
 * put the stuff into a kafka topic (question and answer), read them again and then do it with akka (async)
 *
 * Get swagger
 *
 * Store credentials in property file properly
 *
 * Fix build issue wrt scala
 *
 * Integrate redis just for learning purpose
 *
 * Wireshark and learn everything
 *
 * OAuth and stuffs other than normal log in
 *
 * Create docker image
 *
 * Configure logback.xml
 *
 * Integrate datadog
 *
 */