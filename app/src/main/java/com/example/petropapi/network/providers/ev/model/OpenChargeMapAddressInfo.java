package com.example.petropapi.network.providers.ev.model;

public class OpenChargeMapAddressInfo {
    private String title;
    private String addressLine1;
    private String town;
    private String stateOrProvince;
    private String postcode;
    private double latitude;
    private double longitude;

    public String getTitle() {
        return title;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getTown() {
        return town;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getPostcode() {
        return postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
