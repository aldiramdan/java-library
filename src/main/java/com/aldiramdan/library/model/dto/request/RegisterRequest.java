package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "username is required")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "username must be alphanumeric and not whitespace")
    private String username;

    @Email(message = "input must be an email format")
    @NotBlank(message = "email is required")
    private String email;

    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "password is required")
    private String password;
}
