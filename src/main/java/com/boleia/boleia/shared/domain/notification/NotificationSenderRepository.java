package com.boleia.boleia.shared.domain.notification;

public interface NotificationSenderRepository {
    void send(String to, String message);
}
