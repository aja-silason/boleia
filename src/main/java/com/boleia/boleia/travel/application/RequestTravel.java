package com.boleia.boleia.travel.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.domain.notification.FirebaseNotificationRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.PassangerIsAlreadyInTravelError;
import com.boleia.boleia.travel.domain.TravelIsFuelError;
import com.boleia.boleia.travel.domain.TravelPassangerStatus;
import com.boleia.boleia.travel.domain.TravelRepository;
import com.boleia.boleia.travel.domain.driver.DriverACL;
import com.boleia.boleia.travel.domain.user.UserACL;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestTravel {
    private final TravelRepository repository;
    private final UserACL userACL;
    private final DriverACL driverACL;
    private final FirebaseNotificationRepository firebaseNotificationRepository;

    @Transactional
    public Result<Void, DomainError> execute(RequestTravelInput input){
        var userOrErr = this.userACL.findById(input.passangerId());
        if(userOrErr.isError()) return Result.error(userOrErr.unwrapError());

        var travelOrErr = this.repository.findById(input.travelId());
        if(travelOrErr.isError()) return Result.error(travelOrErr.unwrapError());

        boolean passangerIsAlreadyInTravel = travelOrErr.unwrap().getPassangers().stream().anyMatch(p -> p.getPassangerId().equals(input.passangerId()));
        if(passangerIsAlreadyInTravel) return Result.error(new PassangerIsAlreadyInTravelError());

        // var driver = driverACL.findById(travelOrErr.unwrap().getDriverId());
        
        var acceptedCount = travelOrErr.unwrap().getPassangers().stream().filter(ps -> ps.getStatus().equals(TravelPassangerStatus.ACCEPTED)).count();
        if(acceptedCount > travelOrErr.unwrap().getSeats()) return Result.error(new TravelIsFuelError());

        var travel = travelOrErr.unwrap();
        travel.requestTravel(userOrErr.unwrap().getId());

        // System.out.println("Olha o token "+ driver.unwrap().getFcm());

        // var sendNotificationOrErr = firebaseNotificationRepository.sendPushNotification(driver.unwrap().getFcm(), travel.getId().toString());
        // if(sendNotificationOrErr.isError()) return Result.error(sendNotificationOrErr.unwrapError());

        var voidOrErr = this.repository.save(travel);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }
}
