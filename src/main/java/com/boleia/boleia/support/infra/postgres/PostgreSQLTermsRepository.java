package com.boleia.boleia.support.infra.postgres;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.TermsModel;
import com.boleia.boleia.shared.jpa.entity.TermsModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.politicsAndTerms.Terms;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLTermsRepository implements TermsRepository {

    private final TermsModelJpa jpa;

    @Override
    public Result<Void, DomainError> save(Terms domain) {
        try {
            this.jpa.save(this.toModel(domain));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao adicionar termos de responsabilidade";
            return Result.error(new DomainError(msg));
        }
    }

    private TermsModel toModel(Terms domain) {
        var model = (domain.getId() != null) ? this.jpa.findById(domain.getId().toString()).orElse(new TermsModel()) : new TermsModel();

        model.setId(model.getId());
        model.setTitle(domain.getTitle());
        model.setDescription(domain.getDescription());
        return model;
    }

}
