package com.boleia.boleia.entity.infra.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.DriverGateway;
import com.boleia.boleia.entity.domain.DriverNotFoundError;
import com.boleia.boleia.entity.domain.DriverOutput;
import com.boleia.boleia.entity.domain.EntityStatus;
import com.boleia.boleia.entity.domain.UserOutput;
import com.boleia.boleia.shared.jpa.entity.DriverModel;
import com.boleia.boleia.shared.jpa.entity.DriverModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSQLDriverGateway implements DriverGateway {
    private final DriverModelJpa jpa;
    
    @Override
    public Result<DriverOutput, DriverNotFoundError> findByIdentificationNumber(String phoneNumber) {
        var model = this.jpa.findByIdentificationNumber(phoneNumber);
        return model.isPresent() 
            ? Result.ok(this.toDriverOutput(model.get())) 
            : Result.error(new DriverNotFoundError());
    }

    @Override
    public Result<DriverOutput, DriverNotFoundError> findById(String id) {
        var model = this.jpa.findById(id);
        return model.isPresent() 
            ? Result.ok(this.toDriverOutput(model.get())) 
            : Result.error(new DriverNotFoundError());
    }

    @Override
    public Result<DriverOutput, DriverNotFoundError> findByPhoneNumber(String phoneNumber) {
        var model = this.jpa.findByUserPhoneNumber(phoneNumber);
        return model.isPresent() 
            ? Result.ok(this.toDriverOutput(model.get())) 
            : Result.error(new DriverNotFoundError());
    }

    @Override
    public Result<List<DriverOutput>, Void> findAll() {
        var models = this.jpa.findAll();
        var out = models.stream().map(this::toDriverOutput).toList();
        return Result.ok(out);
    }

    private DriverOutput toDriverOutput(DriverModel model) {
        return new DriverOutput(
            model.getId().toString(), 
            model.getIdentificationNumber(), 
            model.getLicenseNumber(), 
            EntityStatus.fromValue(model.getStatus()), 
            model.getUser() != null ? this.toUserOutput(model.getUser()) : null,
            model.getCreatedAt().toString(),
            model.getUpdatedAt().toString());
    }

    private UserOutput toUserOutput(UserModel model) {
        return new UserOutput(
            model.getId().toString(),
            model.getFirstName(),
            model.getLastName(),
            model.getPhoneNumber(),
            model.isDriver(),
            model.getCreatedAt().toString(),
            model.getUpdatedAt().toString()
        );
    }

}
