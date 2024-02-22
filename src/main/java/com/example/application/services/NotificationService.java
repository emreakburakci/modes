package com.example.application.services;

import com.example.application.data.entities.Notification;
import com.example.application.data.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    public void saveNotification(Notification notification) {

        this.notificationRepository.save(notification);

    }

    public void deleteNotification(Notification notification) {

        this.notificationRepository.delete(notification);
    }

    public List<Notification> findAllNotifications(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return notificationRepository.findAll();
        } else {
            return notificationRepository.search(stringFilter);
        }
    }

    public Notification findById(Long notificationId) {

        return notificationRepository.findById(notificationId).get();
    }
}
