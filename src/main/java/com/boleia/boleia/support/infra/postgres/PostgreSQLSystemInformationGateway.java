package com.boleia.boleia.support.infra.postgres;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.jpa.entity.SystemInformationModel;
import com.boleia.boleia.shared.jpa.entity.SystemInformationModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.system.SystemInformationGateway;
import com.boleia.boleia.support.domain.system.SystemInformationOutput;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLSystemInformationGateway implements SystemInformationGateway {
    private final SystemInformationModelJpa jpa;
    private final UserModelJpa userJpa;


    @Override
    public Result<SystemInformationOutput, Void> findInformation() {
        var model = this.jpa.findAll();
        var out = model.stream().map(this::toOutput).findFirst();
        return Result.ok(out.get());
    }

    private SystemInformationOutput toOutput(SystemInformationModel model) {
        var totalUserInSystem = this.userJpa.findAll().size();

        return new SystemInformationOutput(
            model.getId(),
            model.getCentralPhoneNumber(),
            model.getApplicationVersion(),
            totalUserInSystem
        );
    }

}
