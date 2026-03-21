package com.boleia.boleia.travel.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.TravelRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinishTravel {
    private final TravelRepository repository;
    
    @Transactional
    public Result<Void, DomainError> execute(UUID id){
        var travelOrErr = this.repository.findById(id);
        if(travelOrErr.isError()) return Result.error(travelOrErr.unwrapError());

        var travel = travelOrErr.unwrap();
        travel.finish();

        var voidOrErr = this.repository.save(travel);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}
