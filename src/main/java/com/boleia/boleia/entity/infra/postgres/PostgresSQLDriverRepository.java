package com.boleia.boleia.entity.infra.postgres;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.Driver;
import com.boleia.boleia.entity.domain.DriverNotFoundError;
import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.EntityStatus;
import com.boleia.boleia.entity.domain.User;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.DriverModel;
import com.boleia.boleia.shared.jpa.entity.DriverModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostgresSQLDriverRepository implements DriverRepository {
    private final DriverModelJpa jpa;
    private final UserModelJpa userModelJpa;

    @Override
    public Result<Driver, DomainError> save(Driver driver) {
        try {
            this.jpa.save(this.toModel(driver));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao salvar motorista no banco de dados: ";
            log.error(msg, e);
            return Result.error(new DomainError(msg));
        }
    }

    @Override
    public Result<Driver, DriverNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toDriverFactory(model.get()))
            : Result.error(new DriverNotFoundError());
    }

    @Override
    public Result<Driver, DriverNotFoundError> findByIdentificationNumber(String identificationNumber) {
        var model = this.jpa.findByIdentificationNumber(identificationNumber);
        return model.isPresent()
            ? Result.ok(this.toDriverFactory(model.get()))
            : Result.error(new DriverNotFoundError());
    }

    @Override
    public Result<Driver, DriverNotFoundError> findByUserId(UUID id) {
        var model = this.jpa.findByUserId(id.toString());
        return model.isPresent()
            ? Result.ok(this.toDriverFactory(model.get()))
            : Result.error(new DriverNotFoundError());
    }

    @Override
    public Result<Driver, DriverNotFoundError> findByPhoneNumber(String phoneNumber) {
        var model = this.jpa.findByUserPhoneNumber(phoneNumber);
        return model.isPresent()
            ? Result.ok(this.toDriverFactory(model.get()))
            : Result.error(new DriverNotFoundError());
    }

    private DriverModel toModel(Driver driver) {
        var model = (driver.getId() != null) 
            ? jpa.findById(driver.getId().toString()).orElse(new DriverModel()) 
            : new DriverModel();
        model.setIdentificationNumber(driver.getIdentificationNumber());
        model.setLicenseNumber(driver.getLicenseNumber());
        model.setPassword(driver.getPassword());
        model.setStatus(driver.getStatus().getValue());
        model.setUser(this.toUserModel(driver.getUser()));
        return model;
    }

    private Driver toDriverFactory(DriverModel model) {
        return Driver.from(
            UUID.fromString(model.getId()), 
            model.getIdentificationNumber(), 
            model.getLicenseNumber(), 
            model.getPassword(), 
            EntityStatus.fromValue(model.getStatus()),
            this.toUserFactory(model.getUser())
        );
    }

    private UserModel toUserModel(User user) {
        var model = (user.getId() != null) 
        ? userModelJpa.findById(user.getId().toString()).orElse(new UserModel()) 
        : new UserModel();

        model.setId(user.getId().toString());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setType(user.getType());
        model.setStatus(user.getStatus().getValue());
        return model;
    }

    private User toUserFactory(UserModel model) {
        return User.from(
            UUID.fromString(model.getId()), 
            model.getFirstName(), 
            model.getLastName(), 
            model.getPhoneNumber(), 
            model.getType(), 
            EntityStatus.fromValue(model.getStatus()));
    }

}
