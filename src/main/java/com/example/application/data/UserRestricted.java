package com.example.application.data;

import com.example.application.data.entities.User;

public class UserRestricted {

    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String country;
    private String region;
    private String subregion;
    private String district;
    private String postalCode;
    private String phoneNumber;

    private byte[] pictureFront;

    public UserRestricted(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.country = user.getCountry();
        this.region = user.getRegion();
        this.subregion = user.getSubregion();
        this.district = user.getDistrict();
        this.phoneNumber = user.getPhoneNumber();
        this.postalCode = user.getPostalCode();
        this.pictureFront = user.getPictureFront();
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPictureFront() {
        return pictureFront;
    }

    public void setPictureFront(byte[] pictureFront) {
        this.pictureFront = pictureFront;
    }
}
