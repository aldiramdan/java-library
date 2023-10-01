package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotNull(message = "author is required")
    private Long author;

    @NotNull(message = "category is required")
    private Long category;

    @NotNull(message = "genre is required")
    private Long genre;

    @NotNull(message = "publisher is required")
    private Long publisher;

    @NotBlank(message = "synopsis is required")
    private String synopsis;
}
