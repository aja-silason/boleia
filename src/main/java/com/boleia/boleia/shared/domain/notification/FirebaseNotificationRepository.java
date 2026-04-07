package com.boleia.boleia.shared.domain.notification;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface FirebaseNotificationRepository {
    public Result<Void, DomainError> sendPushNotification(String targetTokenFCM, String travel);
}
