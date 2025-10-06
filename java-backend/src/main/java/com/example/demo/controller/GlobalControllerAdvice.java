package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import com.example.demo.dto.BackendError;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BackendError> handle(IllegalArgumentException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(toBackendError(exception, request));
    }

    private BackendError toBackendError(Throwable exception, WebRequest request) {
        String uri = null;
        String method = null;

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            uri = httpServletRequest.getRequestURI();
            method = httpServletRequest.getMethod();
        }

        return BackendError.builder()
            .uri(uri)
            .method(method)
            .message(ExceptionUtils.getMessage(exception))
            .stacktrace(ExceptionUtils.getStackTrace(exception))
            .causeMessage(ExceptionUtils.getRootCauseMessage(exception))
            .timestamp(LocalDateTime.now())
            .build();
    }
}
