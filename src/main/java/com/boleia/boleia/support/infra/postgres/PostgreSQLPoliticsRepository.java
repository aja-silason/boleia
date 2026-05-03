package com.boleia.boleia.support.infra.postgres;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.TermsModel;
import com.boleia.boleia.shared.jpa.entity.TermsModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.politicsAndTerms.Politics;
import com.boleia.boleia.support.domain.politicsAndTerms.PoliticsRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLPoliticsRepository implements PoliticsRepository {

    private final TermsModelJpa jpa;

    @Override
    public Result<Void, DomainError> save(Politics domain) {
        try {
            this.jpa.save(this.toModel(domain));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao adicionar politicas de privacidade";
            return Result.error(new DomainError(msg));
        }
    }

    private TermsModel toModel(Politics domain) {
        var model = (domain.getId() != null) ? this.jpa.findById(domain.getId().toString()).orElse(new TermsModel()) : new TermsModel();

        model.setId(model.getId());
        model.setTitle(domain.getTitle());
        model.setDescription(domain.getDescription());
        return model;
    }

}
