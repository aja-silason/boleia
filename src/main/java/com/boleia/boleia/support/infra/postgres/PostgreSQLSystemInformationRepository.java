package com.boleia.boleia.support.infra.postgres;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.SystemInformationModel;
import com.boleia.boleia.shared.jpa.entity.SystemInformationModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.system.SystemInformation;
import com.boleia.boleia.support.domain.system.SystemInformationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLSystemInformationRepository implements SystemInformationRepository {

    private final SystemInformationModelJpa jpa;

    @Override
    public Result<Void, DomainError> save(SystemInformation domain) {
        try {
            this.jpa.save(this.toModel(domain));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao salvar informações do sistema";
            return Result.error(new DomainError(msg));
        }
    }

    private SystemInformationModel toModel(SystemInformation domain) {
        var model = (domain.getId() != null) ? this.jpa.findById(domain.getId().toString()).orElse(new SystemInformationModel()) : new SystemInformationModel();

        model.setId(model.getId());
        model.setApplicationVersion(domain.getAplicationVersion());
        model.setCentralPhoneNumber(domain.getCentralPhoneNumber());
        return model;
    }

}
