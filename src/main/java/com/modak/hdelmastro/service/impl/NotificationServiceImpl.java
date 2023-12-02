package com.modak.hdelmastro.service.impl;

import com.modak.hdelmastro.domain.NotificationValidator;
import com.modak.hdelmastro.infrastructure.Gateway;
import com.modak.hdelmastro.model.NotificationDto;
import com.modak.hdelmastro.model.exception.NoRuleException;
import com.modak.hdelmastro.service.NotificationService;
import com.modak.hdelmastro.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final KieContainer kieContainer;
    private final Gateway gateway;
    private final ValidatorService validatorService;

    public NotificationServiceImpl(KieContainer kieContainer, Gateway gateway, ValidatorService validatorService) {
        this.kieContainer = kieContainer;
        this.gateway = gateway;
        this.validatorService = validatorService;
    }

    @Override
    public void send(NotificationDto notification) {
        log.info("Sending notification {} to {}", notification.getType(), notification.getRecipient());
        List<LocalDateTime> notificationsTime = validatorService.getNotificationsTime(notification.getRecipient());

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(notification);
        NotificationValidator notificationValidator = NotificationValidator.builder().build();
        kieSession.setGlobal("notificationValidator", notificationValidator);
        kieSession.setGlobal("notificationsTime", notificationsTime);

        int countRules = kieSession.fireAllRules();

        if (countRules == 0) {
            log.error("No rules fired for notification {} to {}", notification.getType(), notification.getRecipient());
            kieSession.dispose();
            throw new NoRuleException("No rules fired for notification", "No rules available to notification type " + notification.getType(), LocalDateTime.now());
        }

        sendNotification(notification, notificationValidator, notificationsTime);

        kieSession.dispose();
    }

    private void sendNotification(NotificationDto notification, NotificationValidator notificationValidator, List<LocalDateTime> notificationsTime) {
        log.info("Sending notification {} to {}", notification.getType(), notification.getRecipient());
        validatorService.validateNotification(notification, notificationValidator, notificationsTime);
        gateway.send(notification.getRecipient(), notification.getMessage());
    }

}
