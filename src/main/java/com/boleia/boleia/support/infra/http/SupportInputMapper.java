package com.boleia.boleia.support.infra.http;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.boleia.boleia.support.application.AtributePolitcsOrTermsInput;
import com.boleia.boleia.support.application.AtributeSystemInformationInput;
import com.boleia.boleia.support.application.RequestSupportInput;

@Component
public class SupportInputMapper {
    public RequestSupportInput toRequestSupportInput(SupportRequest body){
        return new RequestSupportInput(UUID.fromString(body.userId()), body.message());
    }

    public AtributeSystemInformationInput toAtributeSystemInformationInput(SystemInformationRequest body){
        return new AtributeSystemInformationInput(body.centralPhoneNumber(), body.systemVersion());
    }

    public AtributePolitcsOrTermsInput toAtributePolitcsOrTermsInput(AtributePolitcsOrTermsRequest body){
        return new AtributePolitcsOrTermsInput(body.title(), body.description());
    }
}
