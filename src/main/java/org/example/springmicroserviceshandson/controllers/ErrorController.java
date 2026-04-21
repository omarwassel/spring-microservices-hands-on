package org.example.springmicroserviceshandson.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.springmicroserviceshandson.domain.dtos.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponseError> handleException(Exception exception) {
        log.error("caught exception:", exception);
        ApiResponseError error = ApiResponseError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("an internal server error occurred")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ApiResponseError> handleIllegalArgumentException(IllegalArgumentException exception) {
        ApiResponseError error = ApiResponseError
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
