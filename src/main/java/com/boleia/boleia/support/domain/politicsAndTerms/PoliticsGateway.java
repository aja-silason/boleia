package com.boleia.boleia.support.domain.politicsAndTerms;

import java.util.List;

import com.boleia.boleia.shared.types.Result;

public interface PoliticsGateway {
    Result<List<TermsAndPolitcsOutput>, Void> getAllPolitics();
}
