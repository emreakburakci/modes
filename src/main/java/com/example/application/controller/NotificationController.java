package com.example.application.controller;

import com.example.application.data.entities.Notification;
import com.example.application.data.entities.User;
import com.example.application.data.entities.UserNotification;
import com.example.application.services.NotificationService;
import com.example.application.services.UserNotificationService;
import com.example.application.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController implements INotificationController {


    UserNotificationService userNotificationService;
    UserService userService;

    NotificationService notificationService;

    public NotificationController(UserNotificationService userNotificationService, UserService userService, NotificationService notificationService) {

        this.userNotificationService = userNotificationService;
        this.userService = userService;
        this.notificationService = notificationService;

    }

    @Override
    @PostMapping("/getUnreadUserNotifications")
    public ResponseEntity<String> findUnreadNotificationsForUser(@RequestParam("identityNumber") String userId) {
        User user = userService.findById(userId);
        List<Notification> notifications = userNotificationService.findUnreadNotificationsForUser(user);
        String json = "";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            json = ow.writeValueAsString(notifications);
            System.out.println("NOTIFICATIONS JSON OBJ: " + json);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(444).body("Error Converting JSON");
        }


        return ResponseEntity.ok().body(json);
    }

    @Override
    @PostMapping("/getReadUserNotifications")
    public ResponseEntity<String> findReadNotificationsForUser(@RequestParam("identityNumber") String userId) {
        User user = userService.findById(userId);
        List<Notification> notifications = userNotificationService.findReadNotificationsForUser(user);
        String json = "";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            json = ow.writeValueAsString(notifications);
            System.out.println("NOTIFICATIONS JSON OBJ: " + json);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(444).body("Error Converting JSON");
        }


        return ResponseEntity.ok().body(json);
    }

    @Override
    public long countUnreadNotificationsForUser(String userId) {
        User user = userService.findById(userId);
        return userNotificationService.countUnreadNotificationsForUser(user);
    }

    @Override
    public long countReadNotificationsForUser(String userId) {
        User user = userService.findById(userId);
        return userNotificationService.countReadNotificationsForUser(user);
    }

    @PostMapping("/setNotificationRead")
    public ResponseEntity<String> setNotificationRead(@RequestParam("identityNumber") String userId, @RequestParam("notificationId") String notificationId) {
        User user = userService.findById(userId);
        Notification notification = notificationService.findById(Long.parseLong(notificationId));
        UserNotification userNotification = userNotificationService.findById(user, notification);
        userNotification.setStatus("read");
        userNotificationService.saveUserNotification(userNotification);


        return ResponseEntity.ok().body("{\"success\": true}");
    }

    @PostMapping("/getUnreadNotificationsCount")
    public ResponseEntity<String> getUnreadNotificationsCount(@RequestParam("identityNumber") String userId) {
        User user = userService.findById(userId);
        long count = userNotificationService.countUnreadNotificationsForUser(user);
        System.out.println("getUnreadNotificationsCount: " + count);
        return ResponseEntity.ok().body("{\"count\": " + count + "}");
    }


}
