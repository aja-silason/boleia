package com.boleia.boleia.shared.infra.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.boleia.boleia.shared.domain.notification.OTP;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

@Component
public class InMemoryOtpRepository {
    
    private final ConcurrentHashMap<String, OTP> storage = new ConcurrentHashMap<>();

    
    public Result<Void, DomainError> save(OTP entry) {
        storage.put(entry.getPhoneNumber(), entry);
        return Result.ok(null);
    }

    public Result<OTP, DomainError> findByPhoneNumber(String phoneNumber){
        var out = storage.get(phoneNumber);
        return Result.ok(out);
    }

    public Result<Void, DomainError> delete(String phoneNumber){
        this.storage.remove(phoneNumber);
        return Result.ok(null);
    }

    public void deleteIfExpired(String phoneNumber) {
        OTP entry = this.storage.get(phoneNumber);
        if(entry != null && entry.isExpired()) {
            this.storage.remove(phoneNumber);
        }
    }

    public Set<String> getAllPhoneNumbers(){
        return storage.keySet();
    }

}
