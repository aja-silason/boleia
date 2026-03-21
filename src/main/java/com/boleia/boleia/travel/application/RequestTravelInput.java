package com.boleia.boleia.travel.application;

import java.util.UUID;

public record RequestTravelInput(
    UUID travelId,
    UUID passangerId
) {}
