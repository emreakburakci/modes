package com.example.application.services;

import com.example.application.data.entities.Notification;
import com.example.application.data.entities.User;
import com.example.application.data.entities.UserNotification;
import com.example.application.data.entities.UserNotificationId;
import com.example.application.data.repositories.UserNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNotificationService {

    UserNotificationRepository userNotificationRepository;


    public UserNotificationService(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }


    public void saveUserNotification(UserNotification userNotification) {
        if (userNotification == null) {
            System.err.println("userNotification is null. Are you sure you have connected your form to the application?");
            return;
        }
        userNotificationRepository.save(userNotification);
    }

    public List<UserNotification> findAllUserNotifications() {
        return userNotificationRepository.findAll();

    }

    public UserNotification findById(User user, Notification notification) {
        UserNotificationId userNotificationId = new UserNotificationId(user, notification);
        UserNotification userNotification = userNotificationRepository.findById(userNotificationId).get();

        System.out.println("USERNOTIFICATIONSERVICE FINDBYID:");
        System.out.println("USERNOTIFICATIONSERVICE USER NOTIFICATION USERID:" + userNotification.getUserNotificationId().getNotificationId());
        System.out.println("USERNOTIFICATIONSERVICE USER ID:" + userNotification.getUser().getIdentityNumber());
        System.out.println("USERNOTIFICATIONSERVICE NOT. ID:" + userNotification.getNotification().getNotificationId());


        return userNotificationRepository.findByUserNotificationId(userNotificationId);
    }


    public void saveAndFlush(UserNotification userNotification) {
        userNotificationRepository.saveAndFlush(userNotification);
    }

    public void delete(UserNotification userNotification) {
        userNotificationRepository.delete(userNotification);
    }

    public List<Notification> findUnreadNotificationsForUser(User user) {
        return userNotificationRepository.findUnreadNotificationsForUser(user);
    }

    public List<Notification> findReadNotificationsForUser(User user) {
        return userNotificationRepository.findReadNotificationsForUser(user);
    }

    public long countUnreadNotificationsForUser(User user) {
        return userNotificationRepository.countUnreadNotificationsForUser(user);
    }

    public long countReadNotificationsForUser(User user) {
        return userNotificationRepository.countReadNotificationsForUser(user);
    }
}
