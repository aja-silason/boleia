package com.boleia.boleia.travel.domain.driver;

import java.util.UUID;

import com.boleia.boleia.shared.types.Result;

public interface DriverACL {
    Result<Driver, DriverNotFoundError> findById(UUID id);
}
