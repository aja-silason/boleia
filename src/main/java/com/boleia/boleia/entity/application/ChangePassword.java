package com.boleia.boleia.entity.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.NonMatchPasswordError;
import com.boleia.boleia.entity.domain.Password;
import com.boleia.boleia.entity.domain.PasswordIsWrongError;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangePassword {
    private final DriverRepository repository;

    public Result<Void, DomainError> execute(ChangePasswordInput input){
        var driverOrErr = this.repository.findById(UUID.fromString(input.driverId()));
        if(driverOrErr.isError()) return Result.error(driverOrErr.unwrapError());

        var aPassword = new Password();
        var passwordMatched = aPassword.matches(input.oldPassword(), driverOrErr.unwrap().getPassword());

        if(!input.oldPassword().equals(input.confirmedPassword())) return Result.error(new NonMatchPasswordError());
        if(!passwordMatched.unwrap()) return Result.error(new PasswordIsWrongError());

        var hashedPassword = aPassword.fromPlainText(input.confirmedPassword());

        var driver = driverOrErr.unwrap();

        driver.changePassword(hashedPassword.unwrap());

        var voidOrErr = this.repository.save(driver);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}
