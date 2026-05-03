package com.boleia.boleia.support.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.politicsAndTerms.Politics;
import com.boleia.boleia.support.domain.politicsAndTerms.PoliticsRepository;
import com.boleia.boleia.support.domain.system.TermsAndPolictsCannotBeEmptyError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtributePolitcs {
    private final PoliticsRepository repository;

    public Result<Void, DomainError> execute(AtributePolitcsOrTermsInput input) {
        if(input.title().isEmpty() || input.title().isBlank()) return Result.error(new TermsAndPolictsCannotBeEmptyError("titulo"));
        if(input.description().isEmpty() || input.description().isBlank()) return Result.error(new TermsAndPolictsCannotBeEmptyError("descrição"));

        var terms = Politics.create(
            input.title(),
            input.description(),
            0);

        var saveOrErr = this.repository.save(terms);
        if(saveOrErr.isError()) return Result.error(saveOrErr.unwrapError());

        return Result.ok(null);
    }
}
