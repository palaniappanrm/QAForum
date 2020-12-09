package com.eceplatform.QAForum.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.eceplatform.QAForum.dto.UserDTO;
import com.eceplatform.QAForum.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfigs {

    @Value("${s3.access_key_id}")
    private String access_key_id;

    @Value("${s3.secret_key_id}")
    private String secret_key_id;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean("userThreadLocal")
    public ThreadLocalUtil<UserDTO> userThreadLocalUtil(){
        return new ThreadLocalUtil<UserDTO>();
    }

    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_key_id);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("ap-south-1")
                .build();
    }
}
