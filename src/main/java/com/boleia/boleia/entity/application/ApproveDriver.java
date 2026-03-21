package com.boleia.boleia.entity.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverNotFoundError;
import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.UserRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApproveDriver {
    private final DriverRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public Result<Void, DomainError> execute(UUID id){
        var driverOrErr = this.repository.findById(id);
        if(driverOrErr.isError()) return Result.error(new DriverNotFoundError());

        var userOrErr = this.userRepository.findById(driverOrErr.unwrap().getUser().getId());
        if(userOrErr.isError()) return Result.error(new DriverNotFoundError());

        var driver = driverOrErr.unwrap();
        var user = userOrErr.unwrap();

        driver.approve();
        user.approve();

        var voidOrErr = this.repository.save(driver);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        var userVoidOrErr = this.userRepository.save(user);
        if(userVoidOrErr.isError()) return Result.error(userVoidOrErr.unwrapError());

        return Result.ok(null);
    }

}
