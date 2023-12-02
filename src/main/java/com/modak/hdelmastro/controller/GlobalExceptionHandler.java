package com.modak.hdelmastro.controller;

import com.modak.hdelmastro.model.ErrorResponseDto;
import com.modak.hdelmastro.model.exception.MaxNotificationException;
import com.modak.hdelmastro.model.exception.NoRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxNotificationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExcpetions(MaxNotificationException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDto
                        .builder()
                        .details(ex.getDetails())
                        .message(ex.getMessage())
                        .timestamp(ex.getTimestamp())
                        .build());
    }

    @ExceptionHandler(NoRuleException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExcpetions(NoRuleException ex) {

        return ResponseEntity.badRequest()
                .body(ErrorResponseDto
                        .builder()
                        .details(ex.getDetails())
                        .message(ex.getMessage())
                        .timestamp(ex.getTimestamp())
                        .build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExcpetions(MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest()
                .body(ErrorResponseDto
                        .builder()
                        .details(ex.getLocalizedMessage())
                        .message(ex.getBody().getDetail())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
