package com.boleia.boleia.support.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.politicsAndTerms.PoliticsGateway;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsAndPolitcsOutput;
import com.boleia.boleia.support.domain.politicsAndTerms.TermsGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoliticsAndTermsFinder {
    private final PoliticsGateway politicsGateway;
    private final TermsGateway termsGateway;

    public Result<List<TermsAndPolitcsOutput>, Void> findAllTerms(){
        return this.termsGateway.getAllTerms();
    }

    public Result<List<TermsAndPolitcsOutput>, Void> findAllPolitcs(){
        return this.politicsGateway.getAllPolitics();
    }
}
