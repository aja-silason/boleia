package com.boleia.boleia.entity.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.Password;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoveryPassword {
    private final DriverRepository repository;

    public Result<Boolean, DomainError> execute(RecoveryPasswordInput input){
        var driverOrErr = this.repository.findByPhoneNumber(input.phoneNumber());
        if(driverOrErr.isError()) return Result.error(driverOrErr.unwrapError());

        var aPassword = new Password();

        var hashedPassword = aPassword.fromPlainText(input.password());
        if(hashedPassword.isError()) return Result.error(hashedPassword.unwrapError());

        var driver = driverOrErr.unwrap();

        driver.changePassword(hashedPassword.unwrap());

        var voidOrErr = this.repository.save(driver);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(true);
    }

}
