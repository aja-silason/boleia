package com.boleia.boleia.travel.domain.user;

import java.util.UUID;

import com.boleia.boleia.shared.types.Result;

public interface UserACL {
    Result<User, UserNotFoundError> findById(UUID id);
}
