package com.example.application.services;

import com.example.application.data.entities.User;
import com.example.application.data.repositories.UserRepository;
import com.example.application.utils.LocationData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LocationConfirmationService {

    private UserRepository userRepository;

    public LocationConfirmationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> confirmLocation(String country, String region, String subregion, String district, String postalCode, String identityNumber) {

        User user = userRepository.findById(identityNumber).orElse(null);
        LocationData usersLocationData = LocationData.getUserLocationData(user);

        if (country.equals(usersLocationData.getCountry()) && (region.equals(usersLocationData.getRegion())) && (subregion.equals(usersLocationData.getSubregion())) && (district.equals(usersLocationData.getDistrict())) && (postalCode.equals(usersLocationData.getPostalCode()))) {

            return ResponseEntity.ok().body("{\"success\": true}");

        }
        return ResponseEntity.ok().body("{\"success\": false}");
    }
}