package com.aldiramdan.library.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseToken {
    private String accessToken;
    private String refreshToken;
}
