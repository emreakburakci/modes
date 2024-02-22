package com.example.application.controller;

import com.example.application.services.LocationConfirmationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationConfirmationController implements ILocationConfirmationController {

    private final LocationConfirmationService locationConfirmationService;

    public LocationConfirmationController(LocationConfirmationService locationConfirmationService) {
        this.locationConfirmationService = locationConfirmationService;
    }

    @PostMapping("/confirmLocation")
    public ResponseEntity<String> confirmLocation(@RequestParam("country") String country, @RequestParam("region") String region, @RequestParam("subregion") String subregion, @RequestParam("district") String district, @RequestParam("postalCode") String postalCode, @RequestParam("identityNumber") String identityNumber) {

        return locationConfirmationService.confirmLocation(country, region, subregion, district, postalCode, identityNumber);
    }
}
