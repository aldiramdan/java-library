package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "username must be alphanumeric and not whitespace")
    private String username;

    @Email(message = "input must be an email format")
    @NotBlank(message = "email is required")
    private String email;
}
