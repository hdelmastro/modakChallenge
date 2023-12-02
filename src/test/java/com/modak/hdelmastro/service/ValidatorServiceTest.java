package com.modak.hdelmastro.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.modak.hdelmastro.domain.NotificationValidator;
import com.modak.hdelmastro.model.NotificationDto;
import com.modak.hdelmastro.model.exception.MaxNotificationException;
import com.modak.hdelmastro.service.impl.ValidatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ValidatorServiceTest {

    @Mock
    private Cache<String, List<LocalDateTime>> caffeineCache;

    @InjectMocks
    private ValidatorServiceImpl target;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNotificationsTime_shouldReturnCachedNotificationsTime() {
        String recipient = "recipient";
        List<LocalDateTime> expectedNotificationsTime = Arrays.asList(LocalDateTime.now(), LocalDateTime.now().minusHours(1));

        when(caffeineCache.getIfPresent(recipient)).thenReturn(expectedNotificationsTime);

        List<LocalDateTime> result = target.getNotificationsTime(recipient);

        assertEquals(expectedNotificationsTime, result);
        verify(caffeineCache).getIfPresent(recipient);
    }

    @Test
    void getNotificationsTime_shouldReturnNullForNonexistentRecipient() {
        String recipient = "nonexistentRecipient";

        when(caffeineCache.getIfPresent(recipient)).thenReturn(null);

        List<LocalDateTime> result = target.getNotificationsTime(recipient);

        assertNull(result);
        verify(caffeineCache).getIfPresent(recipient);
    }

    @Test
    void validateNotification_shouldUpdateCacheWithNewNotification() {
        NotificationDto notification = NotificationDto.builder()
                .type("type")
                .recipient("recipient")
                .build();

        NotificationValidator notificationValidator = NotificationValidator.builder()
                .maxNotifications(2)
                .period(1)
                .timeUnit(java.util.concurrent.TimeUnit.HOURS)
                .build();

        List<LocalDateTime> notificationsTime = Collections.emptyList();

        target.validateNotification(notification, notificationValidator, notificationsTime);

        verify(caffeineCache).put(eq(notification.getRecipient()), anyList());
    }

    @Test
    void validateNotification_shouldThrowMaxNotificationExceptionWhenMaxNotificationsReached() {
        NotificationDto notification = NotificationDto.builder()
                .type("type")
                .recipient("recipient")
                .build();

        NotificationValidator notificationValidator =NotificationValidator.builder()
                .maxNotifications(1)
                .period(1)
                .timeUnit(java.util.concurrent.TimeUnit.HOURS)
                .build();

        List<LocalDateTime> notificationsTime = Arrays.asList(LocalDateTime.now());

        assertThrows(MaxNotificationException.class, () ->
                target.validateNotification(notification, notificationValidator, notificationsTime));

        verify(caffeineCache, never()).put(anyString(), anyList());
    }
}