package com.boleia.boleia.entity.application;

public record RecoveryPasswordInput(
    String phoneNumber,
    String password
) {}
