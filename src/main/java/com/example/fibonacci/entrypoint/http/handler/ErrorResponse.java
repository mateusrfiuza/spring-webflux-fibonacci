package com.example.fibonacci.entrypoint.http.handler;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, String message) {}
