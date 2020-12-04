package com.eceplatform.QAForum.util;

import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.model.User;
import com.eceplatform.QAForum.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userThreadLocal")
    private ThreadLocalUtil<UserDTO> userThreadLocal;

    @Autowired
    private JwtTokenUtil jwtTokenUtill;

    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            Optional<Cookie> loggedInCookie = Arrays.asList(cookies).stream().filter(cookie -> cookie.getName().equals("loggedIn_token")).findFirst();
            if(loggedInCookie.isPresent()){
                String token = loggedInCookie.get().getValue();
                if(!jwtTokenUtill.isTokenExpired(token)){
                    String email = jwtTokenUtill.getUserEmailFromToken(token);
                    User user = userRepository.findByEmail(email);
                    userThreadLocal.setValue(new UserDTO(user.getId(), user.getName(), user.getEmail()));
                }
            }
        }

        log.info("Before Handler execution");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userThreadLocal.clear();
    }
}