package com.boleia.boleia.support.domain.politicsAndTerms;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface PolitcsRepository {
    Result<Void, DomainError> save(Politics domain);
}
