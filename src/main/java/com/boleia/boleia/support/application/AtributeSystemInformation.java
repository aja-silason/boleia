package com.boleia.boleia.support.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.system.SystemDataInformationCannotBeEmptyError;
import com.boleia.boleia.support.domain.system.SystemInformation;
import com.boleia.boleia.support.domain.system.SystemInformationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtributeSystemInformation {

    private final SystemInformationRepository repository;

    public Result<Void, DomainError> execute(AtributeSystemInformationInput input){
        if(input.centralPhoneNumber().isEmpty() || input.centralPhoneNumber().isBlank()) return Result.error(new SystemDataInformationCannotBeEmptyError("Número da central"));
        if(input.systemVersion().isEmpty() || input.systemVersion().isBlank()) return Result.error(new SystemDataInformationCannotBeEmptyError("Versão do syistema"));

        var information = SystemInformation.create(input.systemVersion(), input.centralPhoneNumber());

        var saveOrErr = this.repository.save(information);
        if(saveOrErr.isError()) return Result.error(saveOrErr.unwrapError());

        return Result.ok(null);
    }

}
