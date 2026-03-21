package com.boleia.boleia.entity.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.Password;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtributePassword {
    private final DriverRepository repository;

    public Result<Void, DomainError> execute(AtributePasswordInput input){
        var driverOrErr = this.repository.findByIdentificationNumber(input.identificationNumber());
        if(driverOrErr.isError()) return Result.error(driverOrErr.unwrapError());

        var aPassword = new Password();

        var hashedPassword = aPassword.fromPlainText(input.password());

        var driver = driverOrErr.unwrap();

        driver.changePassword(hashedPassword);

        var voidOrErr = this.repository.save(driver);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}
