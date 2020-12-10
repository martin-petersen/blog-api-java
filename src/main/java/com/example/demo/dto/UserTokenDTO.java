package com.example.demo.dto;

public class UserTokenDTO {
    private Long userId;
    private TokenDTO tokenDTO;

    public UserTokenDTO(Long userId, TokenDTO tokenDTO) {
        this.userId = userId;
        this.tokenDTO = tokenDTO;
    }

    public Long getUserId() {
        return userId;
    }

    public TokenDTO getTokenDTO() {
        return tokenDTO;
    }
}
