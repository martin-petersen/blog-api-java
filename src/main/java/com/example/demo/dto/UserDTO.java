package com.example.demo.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {
    @Length(min = 8,message = "displayName length must be at least 8 characters long")
    private String displayName;
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    private String email;
    @NotNull(message = "password is required")
    @NotEmpty(message = "password is required")
    @Length(min = 6, message = "password length must have at least 6 characters")
    private String password;
    private String image;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public String getDisplayName() {
        return displayName;
    }
}
