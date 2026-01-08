package com.example.petropapi.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stations")
public class StationEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String addressLine1;
    private double latitude;
    private double longitude;
    private double queryLatitude;
    private double queryLongitude;
    private long lastUpdated;

    public StationEntity(@NonNull String id,
                         String name,
                         String addressLine1,
                         double latitude,
                         double longitude,
                         double queryLatitude,
                         double queryLongitude,
                         long lastUpdated) {
        this.id = id;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.latitude = latitude;
        this.longitude = longitude;
        this.queryLatitude = queryLatitude;
        this.queryLongitude = queryLongitude;
        this.lastUpdated = lastUpdated;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getQueryLatitude() {
        return queryLatitude;
    }

    public double getQueryLongitude() {
        return queryLongitude;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }
}
