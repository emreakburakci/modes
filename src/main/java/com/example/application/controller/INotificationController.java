package com.example.application.controller;

import org.springframework.http.ResponseEntity;

public interface INotificationController {

    ResponseEntity<String> findUnreadNotificationsForUser(String userId);

    ResponseEntity<String> findReadNotificationsForUser(String userId);

    long countUnreadNotificationsForUser(String userId);


    long countReadNotificationsForUser(String userId);


}
