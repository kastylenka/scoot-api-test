package com.kastylenka.scootapi.scootapitest.controller;

import com.kastylenka.scootapi.scootapitest.aspect.RateLimited;
import com.kastylenka.scootapi.scootapitest.model.request.CalculateDistanceRequest;
import com.kastylenka.scootapi.scootapitest.model.response.CalculateDistanceResponse;
import com.kastylenka.scootapi.scootapitest.service.GeoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/scoot/api")
public class GeoController {

    private final GeoService geoService;

    @PostMapping("/calculations/distance")
    @RateLimited(name = "calculations-distance")
    public CalculateDistanceResponse calculateDistance(@RequestBody CalculateDistanceRequest request) {
        return new CalculateDistanceResponse(geoService.calculateDistance(request.startPoint(), request.endPoint()));
    }
}
