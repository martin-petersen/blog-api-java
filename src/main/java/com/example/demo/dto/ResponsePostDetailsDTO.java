package com.example.demo.dto;

import java.time.LocalDateTime;

public class ResponsePostDetailsDTO {
    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDateTime published;
    private final LocalDateTime updated;
    private final UserDetailsDTO userDetailsDTO;

    public ResponsePostDetailsDTO(Long postId, String title, String content, LocalDateTime published, LocalDateTime updated, UserDetailsDTO userDetailsDTO) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.published = published;
        this.updated = updated;
        this.userDetailsDTO = userDetailsDTO;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public UserDetailsDTO getUserDetailsDTO() {
        return userDetailsDTO;
    }
}
