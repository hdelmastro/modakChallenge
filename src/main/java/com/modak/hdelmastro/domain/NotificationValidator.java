package com.modak.hdelmastro.domain;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class NotificationValidator {
    private Integer maxNotifications;
    private Integer period;
    private TimeUnit timeUnit;
}
