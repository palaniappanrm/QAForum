package com.eceplatform.QAForum.controller;

import com.eceplatform.QAForum.dto.LoginRequest;
import com.eceplatform.QAForum.dto.RegisterRequest;
import com.eceplatform.QAForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    // register -> get user name, password, email -> validate email and store hash or encrypted password
    // TODO -> send welcome mail with a link using MailActor so that the user can click that to activate the account
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest){
        try {
            userService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(ValidationException validationException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
//    /login -> Login with username and password
//    TODO : => have to set JWT cookie back, add a record to user session table to track logged in users
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        try {
            userService.login(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(ValidationException validationException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    TODO: /logout -> remove record from user session table and remove the cookie
//    TODO: /resetpassword -> get email address, send email with a link and save that link keyword along with user id to a new table so that we can retrieve later -> send mail
//    TODO: /forgotpassword -> get old and new password -> send mail

}
