package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginDTO {
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is not allowed to be empty")
    private String email;
    @NotNull(message = "password is required")
    @NotEmpty(message = "password is not allowed to be empty")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
