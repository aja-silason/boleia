package com.boleia.boleia.shared.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.domain.notification.OTP;
import com.boleia.boleia.shared.domain.notification.OtpRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.infra.service.InMemoryOtpRepository;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendOTP {
    private final InMemoryOtpRepository repository;
    private final OtpRepository otpRepository;

    public Result<Void, DomainError> execute(String phone){
        String otp = this.generateOpt();

        long expiredAt = System.currentTimeMillis() + (5 * 60 * 1000);

        OTP entry = OTP.buildOtp(otp, phone, expiredAt);
        repository.save(entry);

        this.otpRepository.send(phone, otp);
        return Result.ok(null);
    }

    private String generateOpt(){
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        return otp;
    }


}
