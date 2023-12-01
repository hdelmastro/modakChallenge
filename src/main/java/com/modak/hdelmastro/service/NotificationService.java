package com.modak.hdelmastro.service;

import com.modak.hdelmastro.model.NotificationDto;

public interface NotificationService {

    void send(NotificationDto notification);
}
