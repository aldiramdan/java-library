package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {
    @NotBlank(message = "name is required")
    private String name;
}
