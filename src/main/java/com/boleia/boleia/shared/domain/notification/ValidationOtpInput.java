package com.boleia.boleia.shared.domain.notification;

public record ValidationOtpInput(
    String phoneNumber,
    String otp
) {}
