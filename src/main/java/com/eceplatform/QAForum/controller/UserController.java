package com.eceplatform.QAForum.controller;

import com.eceplatform.QAForum.dto.LoginRequest;
import com.eceplatform.QAForum.dto.RegisterRequest;
import com.eceplatform.QAForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.Duration;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    // register -> get user name, password, email -> validate email and store hash or encrypted password
    // send welcome mail with a link using MailActor so that the user can click that to activate the account
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
//    /login -> Login with username and password, set JWT cookie back
//    TODO : => add a record to user session table with JWT to track logged in users
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){
        try {
            String jwtToken = userService.login(loginRequest);
            ResponseCookie cookie = ResponseCookie.from("loggedIn_token", jwtToken).httpOnly(true).maxAge(Duration.ofDays(1)).build();
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
        } catch(ValidationException validationException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//  confirm-account -> get a token from query param, check in db for valid till time and if it's valid, activate the account and send successfull response
    @GetMapping("/confirm-account")
    public ResponseEntity confirmAccount(@RequestParam(value = "token", required = true) String token){
        try {
            userService.confirmAccount(token);
            return ResponseEntity.ok().body("Registration successful");
        } catch(ValidationException validationException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    TODO: /logout -> remove record from user session table and remove the cookie
//    TODO: /reset-password -> get email address, send email with a link and save that link keyword along with user id to a new table so that we can retrieve later -> send mail
//    TODO: /forgotpassword -> get old and new password -> send mail

}
