package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostDTO {
    @NotNull(message = "title is required")
    @NotEmpty(message = "title is required")
    private String title;
    @NotNull(message = "content is required")
    @NotEmpty(message = "content is required")
    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
