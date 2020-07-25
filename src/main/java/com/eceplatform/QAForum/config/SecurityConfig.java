package com.eceplatform.QAForum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.eceplatform.QAForum.oauth2.CustomAuthSuccessHandler;

@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomAuthSuccessHandler customAuthSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().anyRequest().authenticated()
		.and()
		.oauth2Login()
		.redirectionEndpoint()
		.and()
		.successHandler(customAuthSuccessHandler);
	}
	
}
