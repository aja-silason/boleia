package com.boleia.boleia.support.domain.system;

import com.boleia.boleia.shared.types.Result;

public interface SystemInformationGateway {
    Result<SystemInformationOutput, Void> findInformation();
}
