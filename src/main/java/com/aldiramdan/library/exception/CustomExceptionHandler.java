package com.aldiramdan.library.exception;

import com.aldiramdan.library.exception.custom.*;
import com.aldiramdan.library.model.dto.response.ResponseError;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    private ResponseError responseError;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        log.warn("Exception: {}", e.getMessage());
        responseError = new ResponseError(500, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.internalServerError().body(responseError);
    }

    @ExceptionHandler(value = FoundException.class)
    public ResponseEntity<ResponseError> handleFound(FoundException e) {
        log.warn("FoundException: {}", e.getMessage());
        responseError = new ResponseError(302, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidation(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        Map<String, Object> mapError = new HashMap<>();
        e.getFieldErrors().forEach(error -> mapError.put(error.getField(), error.getDefaultMessage()));
        responseError = new ResponseError(400, LocalDateTime.now(), "Error validation", mapError);
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseError> handleBadRequestException(BadRequestException e) {
        log.warn("BadRequestException: {}", e.getMessage());
        responseError = new ResponseError(400, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorized(UnauthorizedException e) {
        log.warn("UnauthorizedException: {}", e.getMessage());
        responseError = new ResponseError(401, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundException e) {
        log.warn("NotFoundException: {}", e.getMessage());
        responseError = new ResponseError(404, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = NotProcessException.class)
    public ResponseEntity<ResponseError> handleNotProcess(NotProcessException e) {
        log.warn("NotProcessException: {}", e.getMessage());
        responseError = new ResponseError(422, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ResponseError> handleIllegalStateException(IllegalStateException e) {
        log.warn("IllegalStateException: {}", e.getMessage());
        responseError = new ResponseError(500, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = MessagingException.class)
    public ResponseEntity<ResponseError> handleMessagingException(MessagingException e) {
        log.warn("MessagingException: {}", e.getMessage());
        responseError = new ResponseError(500, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = TemplateInputException.class)
    public ResponseEntity<ResponseError> handleTemplateInputException(TemplateInputException e) {
        log.warn("TemplateInputException: {}", e.getMessage());
        responseError = new ResponseError(500, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }
}
