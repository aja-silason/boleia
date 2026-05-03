package com.boleia.boleia.support.domain.system;

import com.boleia.boleia.shared.error.DomainError;

public class TermsAndPolictsCannotBeEmptyError extends DomainError {
    public TermsAndPolictsCannotBeEmptyError(String msg) {
        super("A informação precisa ser fornecida "+ msg);
    }
}
