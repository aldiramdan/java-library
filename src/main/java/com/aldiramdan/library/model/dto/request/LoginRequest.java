package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "username must be alphanumeric and not whitespace")
    private String username;

    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "password is required")
    private String password;
}
