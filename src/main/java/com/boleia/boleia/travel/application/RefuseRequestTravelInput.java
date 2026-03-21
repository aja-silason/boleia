package com.boleia.boleia.travel.application;

import java.util.UUID;

public record RefuseRequestTravelInput(
    UUID travelId,
    UUID passangerId
) {}
