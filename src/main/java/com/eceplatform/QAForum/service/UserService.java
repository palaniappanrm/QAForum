package com.eceplatform.QAForum.service;

import com.eceplatform.QAForum.dto.LoginRequest;
import com.eceplatform.QAForum.dto.RegisterRequest;

public interface UserService {
    void register(RegisterRequest registerRequest);

    String login(LoginRequest loginRequest);

    void confirmAccount(String token);
}
