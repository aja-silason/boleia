package com.boleia.boleia.shared.infra.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.boleia.boleia.shared.domain.notification.OtpRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.Getter;

@Getter
@Component
public class WhatsAppSender implements OtpRepository {

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String apiToken;

    private final RestClient restClient;

    public WhatsAppSender(RestClient.Builder restClientBuilder){
        this.restClient = restClientBuilder.build();
    }

    @Override
    public void send(String to, String message) {
        this.sendOtp(to, message);
    }

    @Override
    public Result<Void, DomainError> sendOtp(String to, String otp) {
        Map<String, Object> body = Map.of(
            "messaging_product", "whatsapp",
            "to", to,
            "type", "template",
            "template", Map.of(
                "name", "BOLEIA",
                "language", Map.of("code", "pt_AO"),
                "components", List.of(
                    Map.of(
                        "type", "body",
                        "parameters", List.of(
                            Map.of("type", "text", "text", otp)
                        )
                    ),
                    Map.of(
                        "type", "button",
                        "sub_type", "url",
                        "index", "0",
                        "parameters", List.of(
                            Map.of("type", "text", "text", otp)
                        )
                    )
                )
            )
        );

        restClient.post()
            .uri(apiUrl)
            .header("Authorization", "Bearer " + apiToken)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .toBodilessEntity();

        return Result.ok(null);

    }


}
