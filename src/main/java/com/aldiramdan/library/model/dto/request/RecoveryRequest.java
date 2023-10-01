package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecoveryRequest {
    @Email(message = "input must be an email format")
    @NotBlank(message = "email is required")
    private String email;
}
