package com.boleia.boleia.entity.domain;

import java.util.UUID;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface DriverRepository {
    Result<Driver, DomainError> save(Driver driver);
    Result<Driver, DriverNotFoundError> findById(UUID id);
    Result<Driver, DriverNotFoundError> findByIdentificationNumber(String identificationNumber);
    Result<Driver, DriverNotFoundError> findByUserId(UUID id);
}
