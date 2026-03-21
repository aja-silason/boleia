package com.boleia.boleia.shared.infra.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "twilio")
public class TwillioProperties {
    private String accountSid;
    private String authToken;
    private String fromPhone;
}
