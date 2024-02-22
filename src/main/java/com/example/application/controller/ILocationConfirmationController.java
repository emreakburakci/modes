package com.example.application.controller;

import org.springframework.http.ResponseEntity;

public interface ILocationConfirmationController {
    ResponseEntity<String> confirmLocation(String country, String region, String subregion, String district, String postalCode, String identityNumber);

}
