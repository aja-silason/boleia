package com.boleia.boleia.travel.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.TravelIsFuelError;
import com.boleia.boleia.travel.domain.TravelPassangerStatus;
import com.boleia.boleia.travel.domain.TravelRepository;
import com.boleia.boleia.travel.domain.user.UserACL;
import com.boleia.boleia.travel.domain.user.UserNotFoundError;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApproveRequestTravel {
    private final TravelRepository repository;
    private final UserACL userACL;

    @Transactional
    public Result<Void, DomainError> execute(ApproveRequestTravelInput input){
        var travelOrErr = this.repository.findById(input.travelId());
        if(travelOrErr.isError()) return Result.error(travelOrErr.unwrapError());

        var existsPassangerInTravel = travelOrErr.unwrap().getPassangers().stream().anyMatch(ps -> ps.getPassangerId().equals(input.passangerId()));

        if(!existsPassangerInTravel) return Result.error(new UserNotFoundError());

        var acceptedCount = travelOrErr.unwrap().getPassangers().stream().filter(ps -> ps.getStatus().equals(TravelPassangerStatus.ACCEPTED)).count();
        if(acceptedCount > travelOrErr.unwrap().getSeats()) return Result.error(new TravelIsFuelError());

        var passagerOrErr = this.userACL.findById(input.passangerId());
        if(passagerOrErr.isError()) return Result.error(passagerOrErr.unwrapError());

        var travel = travelOrErr.unwrap();
        travel.acceptPassenger(input.passangerId());

        var voidOrErr = this.repository.save(travel);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}
