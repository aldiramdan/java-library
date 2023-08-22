package com.aldiramdan.library.exception;

import com.aldiramdan.library.exception.custom.*;
import com.aldiramdan.library.model.dto.response.ResponseError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandler {
    private ResponseError responseError;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseError> handleException(Exception e) {
        responseError = new ResponseError(500, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.internalServerError().body(responseError);
    }

    @ExceptionHandler(value = FoundException.class)
    public ResponseEntity<ResponseError> handleFound(FoundException e) {
        responseError = new ResponseError(302, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidation(MethodArgumentNotValidException e) {
        Map<String, Object> mapError = new HashMap<>();
        e.getFieldErrors().forEach(error -> mapError.put(error.getField(), error.getDefaultMessage()));
        responseError = new ResponseError(400, LocalDateTime.now(), "Error validation", mapError);
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseError> handleBadRequestException(BadRequestException e) {
        responseError = new ResponseError(400, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorized(UnauthorizedException e) {
        responseError = new ResponseError(401, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundException e) {
        responseError = new ResponseError(404, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }

    @ExceptionHandler(value = NotProcessException.class)
    public ResponseEntity<ResponseError> handleNotProcess(NotProcessException e) {
        responseError = new ResponseError(422, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getCode()).body(responseError);
    }
}
