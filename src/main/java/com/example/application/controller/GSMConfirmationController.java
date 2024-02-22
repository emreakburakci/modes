package com.example.application.controller;

import com.example.application.services.GSMConfirmationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GSMConfirmationController implements IGSMConfirmationController {

    private GSMConfirmationService gsmConfirmationService;

    public GSMConfirmationController(GSMConfirmationService gsmConfirmationService) {

        this.gsmConfirmationService = gsmConfirmationService;

    }

    @PostMapping("/confirmGSM")
    public ResponseEntity<String> confirmGSM(@RequestParam("enteredCode") String enteredCode, @RequestParam("identityNumber") String identityNumber) {

        return gsmConfirmationService.confirmGSM(enteredCode, identityNumber);
    }

    @PostMapping("/requestGSMCode")
    public ResponseEntity<String> requestGSMCode(@RequestParam("identityNumber") String identityNumber) {
        return gsmConfirmationService.requestGSMCode(identityNumber);
    }

    @PostMapping("/clearGSMCode")
    public ResponseEntity<String> clearGSMCode(@RequestParam("identityNumber") String identityNumber) {
        return gsmConfirmationService.clearGSMCode(identityNumber);
    }


}


