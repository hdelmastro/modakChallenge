package com.modak.hdelmastro.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.modak.hdelmastro.domain.NotificationValidator;
import com.modak.hdelmastro.model.NotificationDto;
import com.modak.hdelmastro.model.exception.MaxNotificationException;
import com.modak.hdelmastro.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ValidatorServiceImpl implements ValidatorService {

    private final Cache<String, List<LocalDateTime>> caffeineCache;

    public ValidatorServiceImpl(Cache<String, List<LocalDateTime>> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    @Override
    public List<LocalDateTime> getNotificationsTime(String recipient) {
        return caffeineCache.getIfPresent(recipient);
    }

    @Override
    public void validateNotification(NotificationDto notification, NotificationValidator notificationValidator, List<LocalDateTime> notificationsTime) {
        List<LocalDateTime> filteredNotificationsTime = filterTimesNotifications(notificationValidator, notificationsTime);

        if (filteredNotificationsTime != null && filteredNotificationsTime.size() >= notificationValidator.getMaxNotifications()) {
            log.error("Max notifications reached for notification {} to {}", notification.getType(), notification.getRecipient());
            throw new MaxNotificationException("Max notifications reached",
                    "Max notifications [" + notificationValidator.getMaxNotifications() + "] reached for notification type " + notification.getType()
                            + " in period " + notificationValidator.getPeriod() + " " + notificationValidator.getTimeUnit().name(),
                    LocalDateTime.now());
        }

        updateCache(notification, filteredNotificationsTime);
    }

    private void updateCache(NotificationDto notification, List<LocalDateTime> notificationsTime) {
        List<LocalDateTime> newNotificationsTime = new ArrayList<>();
        newNotificationsTime.add(LocalDateTime.now());
        if (notificationsTime != null) {
            newNotificationsTime.addAll(notificationsTime);
        }
        caffeineCache.put(notification.getRecipient(), newNotificationsTime);
    }

    private List<LocalDateTime> filterTimesNotifications(NotificationValidator notificationValidator, List<LocalDateTime> notificationsTime) {
        if (notificationsTime == null || notificationsTime.isEmpty()) {
            return new ArrayList<>();
        }
        return notificationsTime.stream()
                .filter(notificationTime ->
                        notificationTime.isAfter(LocalDateTime.now().minus(notificationValidator.getPeriod(),
                                notificationValidator.getTimeUnit().toChronoUnit()))).toList();
    }
}
