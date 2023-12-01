package com.modak.hdelmastro.controller;

import com.modak.hdelmastro.model.NotificationDto;
import com.modak.hdelmastro.service.NotificationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody @Valid NotificationDto notification) {
        notificationService.send(notification);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
