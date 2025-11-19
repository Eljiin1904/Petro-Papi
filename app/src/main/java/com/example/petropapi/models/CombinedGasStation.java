package com.example.petropapi.models;

public class CombinedGasStation {

    private String placeId;
    private String name;
    // private FuelOptions fuelOptions;  // REMOVED
    private double latitude;
    private double longitude;

    // Getters and Setters
    public String getPlaceId() {
        return placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // REMOVED: getFuelOptions() / setFuelOptions()

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
