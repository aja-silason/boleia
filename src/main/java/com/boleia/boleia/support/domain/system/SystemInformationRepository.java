package com.boleia.boleia.support.domain.system;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface SystemInformationRepository {
    Result<Void, DomainError> save(SystemInformation domain);
}
