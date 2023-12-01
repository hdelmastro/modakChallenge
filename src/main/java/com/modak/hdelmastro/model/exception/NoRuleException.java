package com.modak.hdelmastro.model.exception;

import java.time.LocalDateTime;

public class NoRuleException extends DefaulNotificationException {


    public NoRuleException(String message, String details, LocalDateTime timestamp) {
        super(message, details, timestamp);
    }
}
