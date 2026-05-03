package com.boleia.boleia.support.infra.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.jpa.entity.PoliticsModel;
import com.boleia.boleia.shared.jpa.entity.PoliticsModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.politicsAndTerms.PoliticsGateway;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsAndPolitcsOutput;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLPoliticsGateway implements PoliticsGateway {
    private final PoliticsModelJpa jpa;

    @Override
    public Result<List<TermsAndPolitcsOutput>, Void> getAllPolitics() {
        var model = this.jpa.findAll();
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);
    }

    private TermsAndPolitcsOutput toOutput(PoliticsModel model) {
        return new TermsAndPolitcsOutput(
            model.getId(),
            model.getTitle(),
            model.getDescription(),
            model.getCreatedAt(),
            model.getUpdatedAt()
        );
    }

}
