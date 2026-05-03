package com.boleia.boleia.support.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.system.SystemInformationGateway;
import com.boleia.boleia.support.domain.system.SystemInformationOutput;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemInformationFinder {
    private final SystemInformationGateway gateway;

    public Result<SystemInformationOutput, Void> getInformation(){
        return this.gateway.findInformation();
    }

}
