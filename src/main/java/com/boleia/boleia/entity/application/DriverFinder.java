package com.boleia.boleia.entity.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverGateway;
import com.boleia.boleia.entity.domain.DriverNotFoundError;
import com.boleia.boleia.entity.domain.DriverOutput;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverFinder {
    private final DriverGateway gateway;

    public Result<List<DriverOutput>, Void> findAll(){
        return this.gateway.findAll();
    }

    public Result<DriverOutput, DriverNotFoundError> findById(String id){
        return this.gateway.findById(id);
    }

    public Result<DriverOutput, DriverNotFoundError> findByIdentificationNumber(String identificationNumber){
        return this.gateway.findByIdentificationNumber(identificationNumber);
    }

    public Result<DriverOutput, DriverNotFoundError> findByPhoneNumber(String phoneNumber){
        return this.gateway.findByPhoneNumber(phoneNumber);
    }

}
