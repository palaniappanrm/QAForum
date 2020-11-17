package com.eceplatform.QAForum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

//    TODO: /register -> get user name, password, email -> validate email and store hash or encrypted password -> send welcome mail
//    TODO: /login -> Login with username and password => have to set JWT cookie back and authentication success message, add a record to user session table to track logged in users
//    TODO: /logout -> remove record from user session table and remove the cookie
//    TODO: /resetpassword -> get email address, send email with a link and save that link keyword along with user id to a new table so that we can retrieve later -> send mail
//    TODO: /forgotpassword -> get old and new password -> send mail

}
