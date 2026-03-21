package com.boleia.boleia.travel.infra.acl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.jpa.entity.DriverModel;
import com.boleia.boleia.shared.jpa.entity.DriverModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.driver.Driver;
import com.boleia.boleia.travel.domain.driver.DriverACL;
import com.boleia.boleia.travel.domain.driver.DriverNotFoundError;

import lombok.RequiredArgsConstructor;

@Service("travel.DriverACL")
@RequiredArgsConstructor
public class DriverACLService implements DriverACL {
    private final DriverModelJpa jpa;

    @Override
    public Result<Driver, DriverNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toDriverFactory(model.get()))
            : Result.error(new DriverNotFoundError());
    }

    private Driver toDriverFactory(DriverModel model) {
        return new Driver(
            UUID.fromString(model.getId())
        ); 
    }
}
