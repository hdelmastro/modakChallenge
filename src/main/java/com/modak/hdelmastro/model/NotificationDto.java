package com.modak.hdelmastro.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDto {
    @NotEmpty
    private String type;
    @NotEmpty
    private String message;
    @NotEmpty
    @Email
    private String recipient;
}
