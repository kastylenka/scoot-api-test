package com.kastylenka.scootapi.scootapitest.service;

import static javax.measure.unit.SI.KILOMETER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.kastylenka.scootapi.scootapitest.model.Coordinate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.measure.unit.SI;
import lombok.RequiredArgsConstructor;
import org.geotools.referencing.GeodeticCalculator;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final GeodeticCalculator geodeticCalculator;

    public BigDecimal calculateDistance(Coordinate startPoint, Coordinate endPoint) {
        try {
            geodeticCalculator.setStartingGeographicPoint(startPoint.longitude(), startPoint.latitude());
            geodeticCalculator.setDestinationGeographicPoint(endPoint.longitude(), endPoint.latitude());
            double distanceInMeters = geodeticCalculator.getOrthodromicDistance();
            return BigDecimal.valueOf(SI.METER.getConverterTo(KILOMETER).convert(distanceInMeters))
                .setScale(4, RoundingMode.HALF_UP);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Bad coordinates");
        }
    }
}
