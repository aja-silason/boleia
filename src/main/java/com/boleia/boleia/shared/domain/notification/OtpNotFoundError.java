package com.boleia.boleia.shared.domain.notification;

import com.boleia.boleia.shared.error.DomainError;

public class OtpNotFoundError extends DomainError {

    public OtpNotFoundError() {
        super("OTP não existe");
    }
}
