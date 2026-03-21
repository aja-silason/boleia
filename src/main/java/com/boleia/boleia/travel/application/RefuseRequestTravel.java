package com.boleia.boleia.travel.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.TravelRepository;
import com.boleia.boleia.travel.domain.user.UserACL;
import com.boleia.boleia.travel.domain.user.UserNotFoundError;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefuseRequestTravel {
    private final TravelRepository repository;
    private final UserACL userACL;

    @Transactional
    public Result<Void, DomainError> execute(RefuseRequestTravelInput input){
        var travelOrErr = this.repository.findById(input.travelId());
        if(travelOrErr.isError()) return Result.error(travelOrErr.unwrapError());

        var existsPassangerInTravel = travelOrErr.unwrap().getPassangers().stream().anyMatch(ps -> ps.getPassangerId().equals(input.passangerId()));
        if(!existsPassangerInTravel) return Result.error(new UserNotFoundError());

        var passagerOrErr = this.userACL.findById(input.passangerId());
        if(passagerOrErr.isError()) return Result.error(passagerOrErr.unwrapError());

        var travel = travelOrErr.unwrap();
        travel.refusePassenger(input.passangerId());

        var voidOrErr = this.repository.save(travel);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}
