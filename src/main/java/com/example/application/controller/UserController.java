package com.example.application.controller;

import com.example.application.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/getUser")
    public ResponseEntity<String> getUser(@RequestParam("identityNumber") String identityNumber) {

        return userService.getUser(identityNumber);

    }

    @PostMapping("/saveConfirmationInfo")

    public ResponseEntity<String> saveConfirmationInfo(@RequestParam("identityNumber") String identityNumber) {
        
        boolean result = userService.saveConfirmationInfo(identityNumber);

        return ResponseEntity.ok().body("{\"success\": " + result + "}");

    }

    @PostMapping("/getUserNotificationsOld")

    public ResponseEntity<String> getUserNotifications(String identityNumber) {

        return userService.getUserNotifications(identityNumber);

    }
}

