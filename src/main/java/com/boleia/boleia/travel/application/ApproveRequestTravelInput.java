package com.boleia.boleia.travel.application;

import java.util.UUID;

public record ApproveRequestTravelInput(
    UUID travelId,
    UUID passangerId
) {}
