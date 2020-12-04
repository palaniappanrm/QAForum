package com.eceplatform.QAForum.service.impl;

import akka.actor.ActorRef;
import com.eceplatform.QAForum.config.ActorConfig;
import com.eceplatform.QAForum.dto.LoginRequest;
import com.eceplatform.QAForum.dto.RegisterRequest;
import com.eceplatform.QAForum.model.User;
import com.eceplatform.QAForum.model.UserToken;
import com.eceplatform.QAForum.repository.UserRepository;
import com.eceplatform.QAForum.repository.UserTokenRepository;
import com.eceplatform.QAForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.eceplatform.QAForum.util.JwtTokenUtil;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ActorConfig actorConfig;

    @Autowired
    private JwtTokenUtil jwtTokenUtill;

    @Autowired
    @Qualifier("mailActor")
    private ActorRef mailActor;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${mail.confirmation.link}")
    private String mailConfirmationLink;

    @Override
    public void register(RegisterRequest registerRequest) {

        User user = userRepository.findByEmail(registerRequest.getEmail());

        if (user != null) {
            throw new ValidationException("User already exists");
        } else {
            String encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());
            user = new User();
            user.setName(registerRequest.getUserName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(encodedPassword);
            userRepository.save(user);

            UserToken userToken = new UserToken(user);
            userTokenRepository.save(userToken);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setSubject("Complete Registration - QA FORUM");
            simpleMailMessage.setText("To confirm your registration, please click on the following link \n" + mailConfirmationLink + userToken.getToken());
            simpleMailMessage.setTo(registerRequest.getEmail());
            mailActor.tell(simpleMailMessage, ActorRef.noSender());
        }
    }

    @Override
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new ValidationException("User doesn't exist");
        } else if(!user.isActive()) {
            throw new ValidationException("User not activated yet");
        } else {
            if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new ValidationException("Login credentials are invalid");
            }
        }
        return jwtTokenUtill.generateToken(user);
    }

    @Override
    public void confirmAccount(String token) {
        UserToken userToken = userTokenRepository.findByToken(token);

        if (userToken != null && userToken.getValidTill().compareTo(LocalDateTime.now()) > 0) {
            User user = userToken.getUser();
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new ValidationException("URL expired, Please register again");
        }
    }
}
