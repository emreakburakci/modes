package com.example.application.data.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

/*
    @JsonIgnore
    @ManyToMany(mappedBy = "notifications", fetch = FetchType.EAGER)
    private Set<User> users;
*/

    @OneToMany(mappedBy = "notification", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserNotification> userNotifications = new HashSet<>();
    private String title;

    private String content;

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    /*
        public Set<User> getUsers() {
            return users;
        }

        public void setUsers(Set<User> users) {
            this.users = users;
        }
    */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<UserNotification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(Set<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }
}
