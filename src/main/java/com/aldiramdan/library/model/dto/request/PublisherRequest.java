package com.aldiramdan.library.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublisherRequest {
    @NotBlank(message = "name is required")
    private String name;
}
