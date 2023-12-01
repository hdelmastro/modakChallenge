package com.modak.hdelmastro.infrastructure;

public interface Gateway {
    void send(String userId, String message);
}
