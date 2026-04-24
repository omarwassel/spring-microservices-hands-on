package org.example.springmicroserviceshandson.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.springmicroserviceshandson.domain.dtos.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleNotFound(EntityNotFoundException ex) {
        ApiResponseError error = ApiResponseError
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Collect all error messages into a single string separated by commas
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ApiResponseError error = ApiResponseError
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
