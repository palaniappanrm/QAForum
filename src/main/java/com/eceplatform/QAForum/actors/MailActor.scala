package com.eceplatform.QAForum.actors

import akka.actor.{Actor, ActorLogging}
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

case class MailActor(var javaMailSender: JavaMailSender) extends Actor with ActorLogging {
  override def receive: Receive = {
    case smm: SimpleMailMessage => {
      log.info("Mail message to be sent: {}", smm)
      try {
        javaMailSender.send(smm)
        log.info("Mail sent successfully: {}", smm)
      } catch {
        case e: Exception =>
          log.error("Error in sending mail: {} for {}", e, smm)
      }
    }
    case _ => log.error("received unknown message");
  }
}