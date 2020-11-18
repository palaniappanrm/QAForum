package com.eceplatform.QAForum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @NotNull(message = "User name can't be null")
    @NotBlank(message = "User name text can't be empty")
    @Size(min = 8, message = "Length of username should be greater than or equal to 8")
    private String userName;

    @NotNull(message = "Email address can't be null")
    @NotBlank(message = "Email address can't be empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    @Size(min = 8, message = "Length of password should be greater than or equal to 8")
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
