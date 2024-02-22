package com.example.application.data.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class UserNotification {

    public UserNotification() {
    }

    public UserNotification(User user, Notification notification) {
        this.userNotificationId = new UserNotificationId(user, notification);
        this.user = user;
        this.notification = notification;
        this.status = "unread";
    }

    @EmbeddedId
    UserNotificationId userNotificationId = new UserNotificationId();

    @ManyToOne
    @MapsId("identityNumber")
    private User user;

    @ManyToOne
    @MapsId("notificationId")
    private Notification notification;

    private String status;

    public UserNotificationId getUserNotificationId() {
        return userNotificationId;
    }

    public void setUserNotificationId(UserNotificationId userNotificationId) {
        this.userNotificationId = userNotificationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
