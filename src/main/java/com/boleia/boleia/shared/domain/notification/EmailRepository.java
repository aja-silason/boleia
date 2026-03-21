package com.boleia.boleia.shared.domain.notification;

public interface EmailRepository extends NotificationSenderRepository {
    void send(String to, String subject, String message);
}
