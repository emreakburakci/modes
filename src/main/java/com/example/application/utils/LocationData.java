package com.example.application.utils;

import com.example.application.data.entities.User;

public class LocationData {
    private String city;
    private String country;
    private String district;
    private String formattedAddress;
    private String isoCountryCode;
    private String name;
    private String postalCode;
    private String region;
    private String street;
    private String streetNumber;
    private String subregion;
    private String timezone;

    public static LocationData getUserLocationData(User user) {

        LocationData locationData = new LocationData();

        locationData.setCountry(user.getCountry());
        locationData.setRegion(user.getRegion());
        locationData.setSubregion(user.getSubregion());
        locationData.setDistrict(user.getDistrict());
        locationData.setPostalCode(user.getPostalCode());

        return locationData;

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCountry())
                .append(getRegion())
                .append(getSubregion())
                .append(getDistrict())
                .append(getPostalCode());

        return builder.toString();

    }


}
