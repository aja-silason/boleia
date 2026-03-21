package com.boleia.boleia.entity.application.auth;

public record SignInInput(
    String phoneNumber,
    String password
) {}
