package com.boleia.boleia.travel.infra.http;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.boleia.boleia.travel.application.ApproveRequestTravelInput;
import com.boleia.boleia.travel.application.CreateTravelInput;
import com.boleia.boleia.travel.application.EvaluateUserInput;
import com.boleia.boleia.travel.application.RefuseRequestTravelInput;
import com.boleia.boleia.travel.application.RequestTravelInput;

@Component
public class TravelInputMapper {
    
    public CreateTravelInput toCreateTravelInput(CreateTravelRequest body) {
        return new CreateTravelInput(body.vehicleId(), body.driverId(), body.dateToTravel(), body.price(), body.origin(), body.destiny(), body.seats());
    }

    public RequestTravelInput toRequestTravelInput(TravelRequest body) {
        return new RequestTravelInput(UUID.fromString(body.travelId()), UUID.fromString(body.passangerId()));
    }

    public ApproveRequestTravelInput toApproveRequestTravelInput(ApproveTravelRequest body) {
        return new ApproveRequestTravelInput(UUID.fromString(body.travelId()), UUID.fromString(body.passangerId()));
    }

    public RefuseRequestTravelInput toRefuseRequestTravelInput(RefuseTravelRequest body) {
        return new RefuseRequestTravelInput(UUID.fromString(body.travelId()), UUID.fromString(body.passangerId()));
    }

    public EvaluateUserInput toEvaluateUserInput(EvaluateUserRequest body){
        return new EvaluateUserInput(body.userId(), body.ratingValue());
    }

}
