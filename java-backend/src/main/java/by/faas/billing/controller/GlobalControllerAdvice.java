package by.faas.billing.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import by.faas.billing.dto.BackendError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public BackendError handle(AuthorizationDeniedException exception, WebRequest request) {
        return toBackendError(exception, request);
    }

    @ExceptionHandler(Exception.class)
    public BackendError handle(Exception exception, WebRequest request) {
        return toBackendError(exception, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BackendError handle(IllegalArgumentException exception, WebRequest request) {
        return toBackendError(exception, request);
    }

    private BackendError toBackendError(Throwable exception, WebRequest request) {
        String uri = null;
        String method = null;

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
            uri = httpServletRequest.getRequestURI();
            method = httpServletRequest.getMethod();
        }

        BackendError error = BackendError.builder()
            .uri(uri)
            .method(method)
            .message(ExceptionUtils.getMessage(exception))
            .stacktrace(ExceptionUtils.getStackTrace(exception))
            .causeMessage(ExceptionUtils.getRootCauseMessage(exception))
            .timestamp(LocalDateTime.now())
            .build();

        log.error("Error uri={}, message={}", error.getUri(), error.getMessage(), exception);

        return error;
    }
}
