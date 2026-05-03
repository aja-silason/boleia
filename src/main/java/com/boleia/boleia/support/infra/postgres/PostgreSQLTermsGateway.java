package com.boleia.boleia.support.infra.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.jpa.entity.TermsModel;
import com.boleia.boleia.shared.jpa.entity.TermsModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsAndPolitcsOutput;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsGateway;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLTermsGateway implements TermsGateway {
    private final TermsModelJpa jpa;

    @Override
    public Result<List<TermsAndPolitcsOutput>, Void> getAllPolitics() {
        var model = this.jpa.findAll();
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);
    }

    private TermsAndPolitcsOutput toOutput(TermsModel model) {
        return new TermsAndPolitcsOutput(
            model.getId(),
            model.getTitle(),
            model.getDescription(),
            model.getCreatedAt(),
            model.getUpdatedAt()
        );
    }

}
