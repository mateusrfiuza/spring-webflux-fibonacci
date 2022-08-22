package com.example.fibonacci.entrypoint.http.handler;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.fibonacci.service.exception.InvalidNumberException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {InvalidNumberException.class, ConstraintViolationException.class})
    protected ResponseEntity<ErrorResponse> handleInvalidInputData(final Exception ex) {
        final var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.status());
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponse> handleGeneralException(final Exception ex) {
        final var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.status());
    }

}
