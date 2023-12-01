package com.modak.hdelmastro.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {

    private String details;
    private String message;
    private LocalDateTime timestamp;
}
