package com.boleia.boleia.support.domain.user;

import com.boleia.boleia.shared.types.Result;

import java.util.UUID;

public interface UserAcl {
    Result<User, UserNotFoundError> findById(UUID id);
}
