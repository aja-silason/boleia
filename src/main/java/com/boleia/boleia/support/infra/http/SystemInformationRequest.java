package com.boleia.boleia.support.infra.http;

public record SystemInformationRequest(
    String centralPhoneNumber,
    String systemVersion
) {}
