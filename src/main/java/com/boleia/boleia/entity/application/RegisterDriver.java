package com.boleia.boleia.entity.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.Driver;
import com.boleia.boleia.entity.domain.DriverIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.EntityType;
import com.boleia.boleia.entity.domain.User;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterDriver {
    private final DriverRepository repository;

    @Transactional
    public Result<Driver, DomainError> execute(RegisterDriverInput input){
        var driverOrErr = this.repository.findByIdentificationNumber(input.identificationNumber());
        if(driverOrErr.isOk()) return Result.error(new DriverIsAlreadyExistsError());

        var user = User.create(
            input.firstName(), input.lastName(), input.phoneNumber(), EntityType.DRIVER);

        var driver = Driver.create(
            input.identificationNumber(),
            input.licenseNumber(),
            user
        );

        var voidOrErr = this.repository.save(driver);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(driver);
    }

}
