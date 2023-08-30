package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeRequest {
    @NotBlank(message = "code is required")
    @Size(max = 6, message = "password must be minimum 8 characters")
    private String code;
}