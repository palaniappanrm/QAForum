package com.eceplatform.QAForum.Actors;

import akka.actor.AbstractLoggingActor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailActor extends AbstractLoggingActor {

    private JavaMailSender javaMailSender;

    public MailActor(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        SimpleMailMessage.class,
                        smm -> {
                            log().info("Mail message to be sent: {}", smm);
                            try {
                                javaMailSender.send(smm);
                                log().info("Mail sent successfully: {}", smm);
                            } catch (Exception e) {
                                log().error("Error in sending mail: {} for {}", e, smm);
                            }
                        })
                .matchAny(o -> log().error("received unknown message"))
                .build();
    }
}