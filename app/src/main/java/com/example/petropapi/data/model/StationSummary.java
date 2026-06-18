package com.example.petropapi.data.model;

import java.util.List;

public class StationSummary {
    private final String id;
    private final String name;
    private final String addressLine1;
    private final String locality;
    private final String region;
    private final String postalCode;
    private final double latitude;
    private final double longitude;
    private final String priceSummary;
    private final String hoursSummary;
    private final String imageUrl;
    private final String providerName;
    private final List<FuelPrice> fuelPrices;

    public StationSummary(String id,
                          String name,
                          String addressLine1,
                          String locality,
                          String region,
                          String postalCode,
                          double latitude,
                          double longitude,
                          String priceSummary,
                          String hoursSummary,
                          String imageUrl,
                          String providerName,
                          List<FuelPrice> fuelPrices) {
        this.id = id;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.locality = locality;
        this.region = region;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.priceSummary = priceSummary;
        this.hoursSummary = hoursSummary;
        this.imageUrl = imageUrl;
        this.providerName = providerName;
        this.fuelPrices = fuelPrices;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPriceSummary() {
        return priceSummary;
    }

    public String getHoursSummary() {
        return hoursSummary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProviderName() {
        return providerName;
    }

    public List<FuelPrice> getFuelPrices() {
        return fuelPrices;
    }
}
