package com.modak.hdelmastro.infrastructure.impl;

import com.modak.hdelmastro.infrastructure.Gateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GatewayImpl implements Gateway {
    @Override
    public void send(String userId, String message) {
        log.info("sending message to user {} ", userId);
    }
}
