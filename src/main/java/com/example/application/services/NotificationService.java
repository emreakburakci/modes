/**
 * NotificationService is a service class responsible for managing notifications in the application.
 * It interacts with the NotificationRepository to perform CRUD operations on Notification entities.
 *
 * @author emreakburakcı
 * @version 1.0
 */

package com.example.application.services;

import com.example.application.data.entities.Notification;
import com.example.application.data.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    /**
     * Constructs a new NotificationService with the given NotificationRepository.
     *
     * @param notificationRepository The repository for notifications.
     */
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Saves a notification to the database.
     *
     * @param notification The notification to be saved.
     */
    public void saveNotification(Notification notification) {

        this.notificationRepository.save(notification);

    }

    /**
     * Deletes a notification from the database.
     *
     * @param notification The notification to be deleted.
     */
    public void deleteNotification(Notification notification) {

        this.notificationRepository.delete(notification);
    }

    /**
     * Retrieves a list of notifications based on a search filter.
     * If the filter is null or empty, returns all notifications.
     *
     * @param stringFilter The filter string to search for notifications.
     * @return A list of notifications matching the filter.
     */
    public List<Notification> findAllNotifications(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return notificationRepository.findAll();
        } else {
            return notificationRepository.search(stringFilter);
        }
    }

    /**
     * Retrieves a notification by its ID.
     *
     * @param notificationId The ID of the notification to retrieve.
     * @return The notification with the specified ID.
     */
    public Notification findById(Long notificationId) {

        return notificationRepository.findById(notificationId).get();
    }
}
