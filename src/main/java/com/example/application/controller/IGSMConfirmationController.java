package com.example.application.controller;

import org.springframework.http.ResponseEntity;

public interface IGSMConfirmationController {

    public ResponseEntity<String> confirmGSM(String enteredCode, String identityNumber);

    public ResponseEntity<String> requestGSMCode(String identityNumber);

    public ResponseEntity<String> clearGSMCode(String identityNumber);

}
