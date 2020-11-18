package com.eceplatform.QAForum.service;

import com.eceplatform.QAForum.dto.LoginRequest;
import com.eceplatform.QAForum.dto.RegisterRequest;

public interface UserService {
    void register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);
}
