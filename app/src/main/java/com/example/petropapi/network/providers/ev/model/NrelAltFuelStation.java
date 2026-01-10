package com.example.petropapi.network.providers.ev.model;

public class NrelAltFuelStation {
    private String id;
    private String stationName;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private Double latitude;
    private Double longitude;
    private String accessDaysTime;
    private String fuelTypeCode;

    public String getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAccessDaysTime() {
        return accessDaysTime;
    }

    public String getFuelTypeCode() {
        return fuelTypeCode;
    }
}
