package com.eceplatform.QAForum.oauth2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.eceplatform.QAForum.model.User;
import com.eceplatform.QAForum.repository.UserRepository;
import com.eceplatform.QAForum.util.JwtTokenUtil;

@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("Authentication SUCCESS!!!!");
		if (response.isCommitted()) {
            return;
        }
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        System.out.println("Email: "+email);
        User user = userRepository.findByEmail(email);
        String token = jwtTokenUtil.generateToken(user);
        String redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8081/")
                .queryParam("auth_token", token)
                .build().toUriString();
        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
	}
	
}
