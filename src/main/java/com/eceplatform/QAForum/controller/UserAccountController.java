package com.eceplatform.QAForum.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.eceplatform.QAForum.dto.UserDto;

@RestController
public class UserAccountController {

	public void signup(@ModelAttribute("user") @Valid UserDto userdto) {
		//TODO
	}
	
	public void signin() {
		//TODO
	}
	
	public void confirmAccount() {
		//TODO
	}
}
