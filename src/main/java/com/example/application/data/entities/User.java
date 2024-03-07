package com.example.application.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//User name can cause exceptions in some SQL implementations  @Entity(name = "UserInfo") can be used
//Dev branch test change comment
//VSCODE DEV BRANCH COMMIT TEST
@Entity
public class User {
    @Id
    private String identityNumber;
    @Version
    private int version;
    @NotEmpty
    private String firstName = "";
    @NotEmpty
    private String password = "";
    @NotEmpty
    private String lastName = "";
    @Email
    @NotEmpty
    private String email = "";
    private LocalDateTime lastConfirmationDateTime;
    private String lastConfirmationStatus;
    private String country;
    private String region;
    private String subregion;
    private String district;
    private String postalCode;

    private String GSMConfirmationCode;

    //+901234567890 format sample
    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserNotification> userNotifications = new HashSet<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] pictureFront;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] pictureLeft;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] pictureRight;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDateTime getLastConfirmationDateTime() {
        return lastConfirmationDateTime;
    }

    public void setLastConfirmationDateTime(LocalDateTime lastConfirmationDateTime) {
        this.lastConfirmationDateTime = lastConfirmationDateTime;
    }

    public String getLastConfirmationStatus() {
        return lastConfirmationStatus;
    }

    public void setLastConfirmationStatus(String lastConfirmationStatus) {
        this.lastConfirmationStatus = lastConfirmationStatus;
    }

    public int getVersion() {
        return version;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPictureFront() {
        return pictureFront;
    }

    public void setPictureFront(byte[] pictureFront) {
        this.pictureFront = pictureFront;
    }

    public byte[] getPictureLeft() {
        return pictureLeft;
    }

    public void setPictureLeft(byte[] pictureLeft) {
        this.pictureLeft = pictureLeft;
    }

    public byte[] getPictureRight() {
        return pictureRight;
    }

    public void setPictureRight(byte[] pictureRight) {
        this.pictureRight = pictureRight;
    }

    public String getGSMConfirmationCode() {
        return GSMConfirmationCode;
    }

    public void setGSMConfirmationCode(String GSMConfirmationCode) {
        this.GSMConfirmationCode = GSMConfirmationCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<UserNotification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(Set<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public String getNotificationStatus(String notificationId) {
        UserNotification userNotification = getUserNotifications().stream().filter(un -> Long.toString(un.getUserNotificationId().getNotificationId()).equals(notificationId)).toList().get(0);

        return userNotification.getStatus();

    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
