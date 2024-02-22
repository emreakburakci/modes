package com.example.application.controller;

import com.example.application.services.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginConfirmationController {

    private LoginService loginService;

    public LoginConfirmationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/authenticateCredentials")
    public boolean authenticateCredentials(@RequestParam(name = "identityNumber") String identityNumber, @RequestParam(name = "password") String password) {
        return loginService.authenticateCredentials(identityNumber, password);

    }
}
