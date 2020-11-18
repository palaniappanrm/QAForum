package com.eceplatform.QAForum.service.impl;

import akka.actor.ActorRef;
import com.eceplatform.QAForum.config.ActorConfig;
import com.eceplatform.QAForum.dto.LoginRequest;
import com.eceplatform.QAForum.dto.RegisterRequest;
import com.eceplatform.QAForum.model.User;
import com.eceplatform.QAForum.repository.UserRepository;
import com.eceplatform.QAForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ActorConfig actorConfig;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void register(RegisterRequest registerRequest) {

        User user = userRepository.findByEmail(registerRequest.getEmail());

        if(user!=null){
            throw new ValidationException("User already exists");
        } else {
            String encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());
            user = new User();
            user.setName(registerRequest.getUserName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(encodedPassword);
            userRepository.save(user);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setSubject("Welcome email");
            simpleMailMessage.setText("You're welcome to our page");
            simpleMailMessage.setTo(registerRequest.getEmail());
            actorConfig.getMailActorRef().tell(simpleMailMessage, ActorRef.noSender());
        }

    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if(user == null){
            throw new ValidationException("User doesn't exist");
        } else {
            if(!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                throw new ValidationException("Login credentials are invalid");
            }
        }
    }
}
