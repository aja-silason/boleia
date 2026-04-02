package com.boleia.boleia.entity.domain;

import java.util.List;

import com.boleia.boleia.shared.types.Result;

public interface DriverGateway {
    Result<List<DriverOutput>, Void> findAll();
    Result<DriverOutput, DriverNotFoundError> findById(String id);
    Result<DriverOutput, DriverNotFoundError> findByIdentificationNumber(String identificationNumber);
    Result<DriverOutput, DriverNotFoundError> findByPhoneNumber(String phoneNumber);
}
