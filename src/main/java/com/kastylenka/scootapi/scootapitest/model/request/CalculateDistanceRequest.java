package com.kastylenka.scootapi.scootapitest.model.request;

import com.kastylenka.scootapi.scootapitest.model.Coordinate;

public record CalculateDistanceRequest(
    Coordinate startPoint,
    Coordinate endPoint
) {

}
