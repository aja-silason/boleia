package com.boleia.boleia.support.domain.user;

import java.util.UUID;

import com.boleia.boleia.shared.types.Result;

public interface UserAcl {
    Result<User, UserNotFoundError> findById(UUID id);
}
