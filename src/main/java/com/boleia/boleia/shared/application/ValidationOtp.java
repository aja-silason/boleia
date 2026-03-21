package com.boleia.boleia.shared.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.domain.notification.OtpExpiredError;
import com.boleia.boleia.shared.domain.notification.OtpNotFoundError;
import com.boleia.boleia.shared.domain.notification.OtpNotMatchError;
import com.boleia.boleia.shared.domain.notification.PayloadIncorrectError;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.infra.service.InMemoryOtpRepository;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidationOtp {
    private final InMemoryOtpRepository repository;

    public Result<Boolean, DomainError> execute(ValidationOtpInput input){

        if(input.phoneNumber() == null || input.otp() == null) return Result.error(new PayloadIncorrectError());

        var entryOrErr = repository.findByPhoneNumber(input.phoneNumber());
        if(entryOrErr.unwrap() == null) return Result.error(new OtpNotFoundError());
        if(entryOrErr.unwrap().isExpired()) return Result.error(new OtpExpiredError());
        
        boolean valid = entryOrErr.unwrap().getOtp().equals(input.otp());
        
        if(!valid) return Result.error(new OtpNotMatchError());

        return Result.ok(valid);

    }
    
}
