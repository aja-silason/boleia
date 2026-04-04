package com.boleia.boleia.travel.application;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.TravelGateway;
import com.boleia.boleia.travel.domain.TravelNotFoundError;
import com.boleia.boleia.travel.domain.TravelOutput;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelFinder {
    private final TravelGateway gateway;

    public Result<TravelOutput, TravelNotFoundError> findById(String id){
        return this.gateway.findById(id);
    }

    public Result<List<TravelOutput>, Void> findAll(){
        return this.gateway.findAll();
    }

    public Result<List<TravelOutput>, Void> findAllByDriver(String id){
        return this.gateway.findAllDriver(id);
    }

    public Result<List<TravelOutput>, Void> findByFilter(String location, Integer seats){
        return this.gateway.findByFilter(location, seats);
    }

}
