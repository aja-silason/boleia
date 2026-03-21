package com.boleia.boleia.travel.domain;

import java.util.UUID;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface TravelRepository {
    Result<Void, DomainError> save(Travel travel);
    Result<Travel, TravelNotFoundError> findById(UUID id);
}
