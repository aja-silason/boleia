package com.boleia.boleia.entity.domain;

import java.util.UUID;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface UserRepository {
    Result<Void, DomainError> save(User user);
    Result<User, UserNotFoundError> findByPhoneNumber(String phoneNumber);
    Result<User, UserNotFoundError> findById(UUID id);
}
