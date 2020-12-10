package com.example.demo.dto;

public class UserDetailsDTO {
    private final Long id;
    private final String displayName;
    private final String email;
    private final String image;

    public UserDetailsDTO(Long id, String displayName, String email, String image) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }
}
