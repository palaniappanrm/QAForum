package com.eceplatform.QAForum.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.eceplatform.QAForum.actors.MailActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class ActorConfig {

    private ActorSystem system = ActorSystem.create("QAForumSystem");

    @Autowired
    private JavaMailSender javaMailSender;

    @Bean("mailActor")
    public ActorRef createMailActor(){
        return system.actorOf(Props.create(MailActor.class, () -> new MailActor(javaMailSender)), "MailActor");
    }
}
