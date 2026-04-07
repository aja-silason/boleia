package com.boleia.boleia.shared.infra.service;

import org.springframework.stereotype.Component;

import com.boleia.boleia.shared.domain.notification.FirebaseNotificationRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Component
public class FirebaseNotificationService implements FirebaseNotificationRepository {

    @Override
    public Result<Void, DomainError> sendPushNotification(String targetTokenFCM, String travelId) {
        Message msg = Message.builder()
            .setToken(targetTokenFCM)
            .setNotification(Notification.builder()
                .setTitle("Nova Solicitação")
                .setBody("Nova solicitação de Boleia")
                .build()
            )
            .putData("travelId", travelId)
            .build();

        try {
            FirebaseMessaging.getInstance().send(msg);
            return Result.ok(null);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return Result.error(new DomainError(e.getMessage()));
        }
    }
}
