package com.example.petropapi.data.model;

public class Trip {
    private final double startLatitude;
    private final double startLongitude;
    private final double endLatitude;
    private final double endLongitude;
    private final long startedAt;

    public Trip(double startLatitude, double startLongitude, double endLatitude, double endLongitude, long startedAt) {
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.startedAt = startedAt;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public long getStartedAt() {
        return startedAt;
    }
}
