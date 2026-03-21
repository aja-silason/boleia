package com.boleia.boleia.shared.domain.notification;

import com.boleia.boleia.shared.error.DomainError;

public class OtpNotMatchError extends DomainError {

    public OtpNotMatchError() {
        super("OTP errado");
    }
}
