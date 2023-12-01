package com.modak.hdelmastro.model.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class DefaulNotificationException extends RuntimeException {


    private final String details;
    private final LocalDateTime timestamp;

    protected DefaulNotificationException(String message, String details, LocalDateTime timestamp) {
        super(message);
        this.timestamp = timestamp;
        this.details = details;
    }
}
