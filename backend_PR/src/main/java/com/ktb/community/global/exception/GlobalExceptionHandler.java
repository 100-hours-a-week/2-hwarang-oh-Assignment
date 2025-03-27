package com.ktb.community.global.exception;

import java.net.URI;

import org.springframework.web.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<Object> handleBaseException(BaseException e, WebRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse
                        .builder(e, errorCode.getStatus(), errorCode.getMessage())
                        .title(e.getClass().getSimpleName())
                        .instance(URI.create(servletWebRequest.getRequest().getRequestURI()))
                        .build());
    }
}
