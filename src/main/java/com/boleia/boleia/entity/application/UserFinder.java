package com.boleia.boleia.entity.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.UserGateway;
import com.boleia.boleia.entity.domain.UserNotFoundError;
import com.boleia.boleia.entity.domain.UserOutput;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFinder {
    
    private final UserGateway gateway;

    public Result<UserOutput, UserNotFoundError> findById(String id) {
        return gateway.findById(id);
    }

    public Result<UserOutput, UserNotFoundError> findByPhoneNumber(String phoneNumber) {
        return gateway.findByPhoneNumber(phoneNumber);
    }

    public Result<List<UserOutput>, Void> findAll() {
        return gateway.findAll();
    }

}
