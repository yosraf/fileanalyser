package com.castlabs.fileanalyser.exception;


import com.castlabs.fileanalyser.model.ApiError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.UnknownHostException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class FileAnalyserExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> violations =exception.getConstraintViolations();
        ApiError error = new ApiError( HttpStatus.BAD_REQUEST,violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException exception){
        ApiError error = new ApiError( HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientResponseException(WebClientResponseException exception){
        ApiError error = new ApiError( HttpStatus.valueOf(exception.getStatusCode().value()),exception.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(exception.getStatusCode().value())).body(error);
    }
    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ApiError> handleUnknownHostException(UnknownHostException exception){
        ApiError error = new ApiError( HttpStatus.BAD_REQUEST,"invalid domain provided");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
