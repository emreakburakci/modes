package com.example.application.controller;

import org.springframework.http.ResponseEntity;

public interface IUserController {

    ResponseEntity<String> getUser(String identityNumber);

    public ResponseEntity<String> getUserNotifications(String identityNumber);

    public ResponseEntity<String> saveConfirmationInfo(String identityNumber);


}
