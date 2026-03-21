package com.boleia.boleia.entity.domain;

import java.util.List;

import com.boleia.boleia.shared.types.Result;

public interface UserGateway {
    Result<UserOutput, UserNotFoundError> findByPhoneNumber(String phoneNumber);
    Result<UserOutput, UserNotFoundError> findById(String id);
    Result<List<UserOutput>, Void> findAll();
}
