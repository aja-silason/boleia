package com.boleia.boleia.shared.infra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.boleia.boleia.shared.domain.notification.OtpRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.Getter;

@Getter
@Component
public class TwillioOtpSender {
    private final String accountSid;
    private final String authToken;
    private final String fromPhone;

    public TwillioOtpSender(
        @Value("${twilio.accountSid}") String accountSid, 
        @Value("${twilio.authToken}") String authToken, 
        @Value("${twilio.phoneNumber}") String fromPhone
    ){
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromPhone = fromPhone;
        Twilio.init(accountSid, authToken);
    }

    public void send(String to, String otp) {
        this.sendOtp(to, otp);
    }

    // @Override
    public Result<Void, DomainError> sendOtp(String to, String otp){
        Message.creator(
            new com.twilio.type.PhoneNumber(to),
            "MG7ee018d9daf6d33c77e91d9a85b46229",
            "Código de validação: " + otp
        ).create();
        return Result.ok(null);
    }

}
