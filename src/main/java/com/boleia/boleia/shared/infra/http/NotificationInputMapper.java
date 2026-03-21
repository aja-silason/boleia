package com.boleia.boleia.shared.infra.http;

import org.springframework.stereotype.Component;

import com.boleia.boleia.shared.application.ValidationOtpInput;

@Component
public class NotificationInputMapper {
    
    public ValidationOtpInput toValidationOtpInput(ValidationOtpRequest body) {
        return new ValidationOtpInput(body.phoneNumber(), body.otp());
    }

}
