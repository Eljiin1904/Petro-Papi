package com.example.petropapi.models.gasbuddy;

import java.util.List;

public class Station {
    private Address address;
    private List<Amenity> amenities;
    private List<Brand> brands;
    private String currency;
    private Object emergencyStatus;  // Use a custom type if needed
    private boolean enterprise;
    private List<String> fuels;
    private boolean hasActiveOutage;
    private StationHours hours;
    private String id;
    private double latitude;
    private double longitude;
    private String name;

    // UPDATED: Prices is now a list of PriceReport objects
    private List<PriceReport> prices;

    public Station() {
    }

    public Station(String id, String name, double latitude, double longitude, Address address) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    // Getters
    public Address getAddress() {
        return address;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public String getCurrency() {
        return currency;
    }

    public Object getEmergencyStatus() {
        return emergencyStatus;
    }

    public boolean isEnterprise() {
        return enterprise;
    }

    public List<String> getFuels() {
        return fuels;
    }

    public boolean isHasActiveOutage() {
        return hasActiveOutage;
    }

    public StationHours getHours() {
        return hours;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    // UPDATED: Getter for prices now returns a List
    public List<PriceReport> getPrices() {
        return prices;
    }
}
