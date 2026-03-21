package com.boleia.boleia.shared.domain.notification;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface OtpRepository extends NotificationSenderRepository{ 
    Result<Void, DomainError> sendOtp(String to, String otp);   
}
