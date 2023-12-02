package com.modak.hdelmastro.service;

import com.modak.hdelmastro.infrastructure.Gateway;
import com.modak.hdelmastro.model.NotificationDto;
import com.modak.hdelmastro.model.exception.NoRuleException;
import com.modak.hdelmastro.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {


    private KieContainer kieContainer = mock(KieContainer.class);
    private Gateway gateway = mock(Gateway.class);
    private ValidatorService validatorService = mock(ValidatorService.class);

    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl(kieContainer, gateway, validatorService);
    }

    @Test
    void send_shouldSendNotificationWhenRulesFired() {
        NotificationDto notification = NotificationDto.builder()
                .type("type")
                .recipient("recipient")
                .message("message")
                .build();

        KieSession kieSession = mock(KieSession.class);
        when(kieContainer.newKieSession()).thenReturn(kieSession);
        when(kieSession.fireAllRules()).thenReturn(1);

        List<LocalDateTime> notificationsTime = Collections.emptyList();
        when(validatorService.getNotificationsTime(notification.getRecipient())).thenReturn(notificationsTime);

        notificationService.send(notification);

        verify(gateway).send(notification.getRecipient(), notification.getMessage());
        verify(kieSession).dispose();
    }

    @Test
    void send_shouldThrowNoRuleExceptionWhenNoRulesFired() {
        NotificationDto notification = NotificationDto.builder()
                .type("type")
                .recipient("recipient")
                .message("message")
                .build();

        KieSession kieSession = mock(KieSession.class);
        when(kieContainer.newKieSession()).thenReturn(kieSession);
        when(kieSession.fireAllRules()).thenReturn(0);

        List<LocalDateTime> notificationsTime = Collections.emptyList();
        when(validatorService.getNotificationsTime(notification.getRecipient())).thenReturn(notificationsTime);

        assertThrows(NoRuleException.class, () ->
                notificationService.send(notification));

        verify(kieSession).dispose();
    }


}