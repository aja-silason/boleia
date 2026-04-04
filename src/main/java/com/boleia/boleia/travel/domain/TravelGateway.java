package com.boleia.boleia.travel.domain;

import java.util.List;

import com.boleia.boleia.shared.types.Result;

public interface TravelGateway {
    Result<List<TravelOutput>, Void> findAll();
    Result<List<TravelOutput>, Void> findAllDriver(String id);
    Result<TravelOutput, TravelNotFoundError> findById(String id);
    Result<List<TravelOutput>, Void> findByFilter(String location, Integer seats);
}
