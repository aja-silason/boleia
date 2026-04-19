package com.boleia.boleia.travel.application;

import java.util.UUID;

public record CancelRequestTravelInput(
    UUID travelId,
    UUID passangerId
) {}
