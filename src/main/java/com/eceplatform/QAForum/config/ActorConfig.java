package com.eceplatform.QAForum.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.eceplatform.QAForum.actors.ESActor;
import com.eceplatform.QAForum.actors.MailActor;
import com.eceplatform.QAForum.repository.QuestionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class ActorConfig {

    private ActorSystem system = ActorSystem.create("QAForumSystem");

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean("mailActor")
    public ActorRef createMailActor(){
        return system.actorOf(Props.create(MailActor.class, () -> new MailActor(javaMailSender)), "MailActor");
    }

    @Bean("esActor")
    public ActorRef createESActor(){
        return system.actorOf(Props.create(ESActor.class, () -> new ESActor(restHighLevelClient, questionRepository, objectMapper)), "ESActor");
    }
}
