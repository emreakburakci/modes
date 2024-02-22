package com.example.application.data.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserNotificationId implements Serializable {

    private String identityNumber;
    private long notificationId;


    public UserNotificationId() {
    }

    public UserNotificationId(User user, Notification notification) {
        this.identityNumber = user.getIdentityNumber();
        this.notificationId = notification.getNotificationId();
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }
}
