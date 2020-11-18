package com.eceplatform.QAForum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "Email address can't be null")
    @NotBlank(message = "Email address can't be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
