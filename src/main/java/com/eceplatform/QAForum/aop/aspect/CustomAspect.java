package com.eceplatform.QAForum.aop.aspect;

import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.util.ThreadLocalUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Aspect
@Component
public class CustomAspect {

    @Autowired
    @Qualifier("userThreadLocal")
    private ThreadLocalUtil<UserDTO> userThreadLocal;

    @Before("@annotation(com.eceplatform.QAForum.aop.annotations.LoggedIn)")
    public void validateLogin(JoinPoint joinPoint) throws Throwable {

        if(userThreadLocal.getValue() == null){
            throw new ValidationException("User not authorized");
        }
    }

}