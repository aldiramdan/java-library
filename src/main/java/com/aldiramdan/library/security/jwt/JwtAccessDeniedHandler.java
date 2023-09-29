package com.aldiramdan.library.security.jwt;

import com.aldiramdan.library.model.dto.response.ResponseError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseError responseError = new ResponseError(HttpServletResponse.SC_FORBIDDEN, e.getMessage(), "Forbidden");
        log.warn("AccessDeniedHandler error: {}", e.getMessage());

        response.resetBuffer();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(responseError));
        response.flushBuffer();
    }
}
