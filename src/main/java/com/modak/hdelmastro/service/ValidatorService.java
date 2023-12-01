package com.modak.hdelmastro.service;

import com.modak.hdelmastro.domain.NotificationValidator;
import com.modak.hdelmastro.model.NotificationDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ValidatorService {
    List<LocalDateTime> getNotificationsTime(String recipient);

    void validateNotification(NotificationDto notification, NotificationValidator notificationValidator, List<LocalDateTime> notificationsTime);
}
