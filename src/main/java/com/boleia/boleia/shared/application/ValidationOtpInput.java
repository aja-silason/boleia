package com.boleia.boleia.shared.application;

public record ValidationOtpInput(
    String phoneNumber,
    String otp
) {}
