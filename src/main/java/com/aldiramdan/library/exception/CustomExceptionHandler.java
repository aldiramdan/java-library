package com.aldiramdan.library.exception;

import com.aldiramdan.library.exception.custom.*;
import com.aldiramdan.library.model.dto.response.ResponseError;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        log.warn("Exception: {}", e.getMessage());
        ResponseError responseError = new ResponseError(500, e.getMessage(), null);
        return ResponseEntity.internalServerError().body(responseError);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        Map<String, Object> mapError = new HashMap<>();
        e.getFieldErrors().forEach(error -> mapError.put(error.getField(), error.getDefaultMessage()));
        ResponseError responseError = new ResponseError(400, "Error validation", mapError);
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseError> handleBadRequestException(BadRequestException e) {
        log.warn("BadRequestException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(400, e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatusCode()).body(responseError);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorizedException(UnauthorizedException e) {
        log.warn("UnauthorizedException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(401, e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatusCode()).body(responseError);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException e) {
        log.warn("NotFoundException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(404, e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatusCode()).body(responseError);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<ResponseError> handleConflictException(ConflictException e) {
        log.warn("ConflictException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(409, e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatusCode()).body(responseError);
    }

    @ExceptionHandler(value = NotProcessException.class)
    public ResponseEntity<ResponseError> handleNotProcessException(NotProcessException e) {
        log.warn("NotProcessException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(422, e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatusCode()).body(responseError);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ResponseError> handleIllegalStateException(IllegalStateException e) {
        log.warn("IllegalStateException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(500, e.getMessage(), null);
        return ResponseEntity.internalServerError().body(responseError);
    }

    @ExceptionHandler(value = MessagingException.class)
    public ResponseEntity<ResponseError> handleMessagingException(MessagingException e) {
        log.warn("MessagingException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(500, e.getMessage(), null);
        return ResponseEntity.internalServerError().body(responseError);
    }

    @ExceptionHandler(value = TemplateInputException.class)
    public ResponseEntity<ResponseError> handleTemplateInputException(TemplateInputException e) {
        log.warn("TemplateInputException: {}", e.getMessage());
        ResponseError responseError = new ResponseError(500, e.getMessage(), null);
        return ResponseEntity.internalServerError().body(responseError);
    }
}
