package com.example.application.controller;

public interface ILoginConfirmationController {
    boolean authenticateCredentials(String identityNumber, String password);
}