package com.eceplatform.QAForum.config;

import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.util.ThreadLocalUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfigs {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean("userThreadLocal")
    public ThreadLocalUtil<UserDTO> userThreadLocalUtil(){
        return new ThreadLocalUtil<UserDTO>();
    }
}
