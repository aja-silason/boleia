package com.boleia.boleia.support.domain.system;

import com.boleia.boleia.shared.error.DomainError;

public class SystemDataInformationCannotBeEmptyError extends DomainError {

    public SystemDataInformationCannotBeEmptyError(String msg) {
        super("A informação precisa ser fornecida "+ msg);
    }
}
