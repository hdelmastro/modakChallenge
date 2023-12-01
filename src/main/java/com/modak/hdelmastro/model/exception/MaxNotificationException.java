package com.modak.hdelmastro.model.exception;

import java.time.LocalDateTime;

public class MaxNotificationException extends DefaulNotificationException {
    public MaxNotificationException(String message, String details, LocalDateTime timestamp) {
        super(message, details, timestamp);
    }
}
