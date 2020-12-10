package com.example.demo.dto;

public class PostDetailsDTO {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;

    public PostDetailsDTO(Long id, Long userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
