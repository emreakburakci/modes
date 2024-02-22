package com.example.application.data.repositories;

import com.example.application.data.entities.Notification;
import com.example.application.data.entities.User;
import com.example.application.data.entities.UserNotification;
import com.example.application.data.entities.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {

    UserNotification findByUserNotificationId(UserNotificationId id);


    @Query("SELECT n FROM Notification n JOIN UserNotification un ON n = un.notification WHERE un.user = :user AND un.status = 'unread'")
        //@Query("SELECT")
    List<Notification> findUnreadNotificationsForUser(@Param("user") User user);

    @Query("SELECT n FROM Notification n JOIN UserNotification un ON n = un.notification WHERE un.user = :user AND un.status = 'read'")
        //@Query("SELECT")
    List<Notification> findReadNotificationsForUser(@Param("user") User user);

    @Query("SELECT COUNT(n) FROM Notification n JOIN UserNotification un ON n = un.notification WHERE un.user = :user AND un.status = 'unread'")
    long countUnreadNotificationsForUser(@Param("user") User user);


    @Query("SELECT COUNT(n) FROM Notification n JOIN UserNotification un ON n = un.notification WHERE un.user = :user AND un.status = 'read'")
    long countReadNotificationsForUser(@Param("user") User user);
}
