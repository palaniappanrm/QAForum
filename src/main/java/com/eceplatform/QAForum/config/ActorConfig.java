package com.eceplatform.QAForum.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.eceplatform.QAForum.Actors.MailActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ActorConfig {

    private ActorRef mailActorRef;

    public ActorConfig(@Autowired JavaMailSender javaMailSender){
        ActorSystem system = ActorSystem.create("TestSystem");
        mailActorRef = system.actorOf(Props.create(MailActor.class, () -> new MailActor(javaMailSender)), "MailActor");
    }

    public ActorRef getMailActorRef() {
        return mailActorRef;
    }
}
