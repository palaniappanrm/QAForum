package com.eceplatform.QAForum.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		System.out.println("user method");
		System.out.println("Principal"+principal.getAttribute("name"));
		principal.getAttributes().forEach((k,v)-> System.out.println("Key: "+k+"Value: "+v));
		return Collections.singletonMap("name", principal.getAttribute("name"));
	}
	
}
