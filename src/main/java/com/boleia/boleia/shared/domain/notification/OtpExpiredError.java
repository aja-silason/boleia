package com.boleia.boleia.shared.domain.notification;

import com.boleia.boleia.shared.error.DomainError;

public class OtpExpiredError extends DomainError {

    public OtpExpiredError() {
        super("OTP expirado");
    }
}
