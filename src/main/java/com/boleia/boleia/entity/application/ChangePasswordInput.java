package com.boleia.boleia.entity.application;

public record ChangePasswordInput(
    String driverId,
    String oldPassword,
    String confirmedPassword
) {}
