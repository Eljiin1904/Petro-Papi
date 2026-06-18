package com.example.petropapi.data.model;

public class LiveData {
    private final String status;
    private final String lastUpdated;

    public LiveData(String status, String lastUpdated) {
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
