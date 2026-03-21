package com.boleia.boleia.shared.workers;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.infra.service.InMemoryOtpRepository;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;



@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class CleanUpOtpConfig {
    private final InMemoryOtpRepository repository;

    @Scheduled(fixedRate = 60_000)
    public Result<Void, DomainError> cleanupExpiredOtp(){
        repository.getAllPhoneNumbers().forEach(phoneNumber -> {
            repository.deleteIfExpired(phoneNumber);
        });
        return Result.ok(null);
    }

}
