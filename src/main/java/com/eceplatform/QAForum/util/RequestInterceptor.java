package com.eceplatform.QAForum.util;

import com.eceplatform.QAForum.repository.UserTokenRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserTokenRepository userTokenRepository;

    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object object, Exception arg3)
            throws Exception {
        log.info("Request is complete");
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object, ModelAndView model)
            throws Exception {
        log.info("Handler execution is complete");
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
            Arrays.asList(cookies).forEach(cookie -> log.info(cookie.getValue()));
        log.info("Before Handler execution");
        return true;
    }

}