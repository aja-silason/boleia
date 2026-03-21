package com.boleia.boleia.travel.domain;

import java.util.UUID;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.user.UserNotFoundError;

public interface RatingRepository {
    Result<Void, DomainError> save(Rating rating);
    Result<Rating, UserNotFoundError> findByUserId(UUID id);
}
