package com.example.petropapi.models.gasbuddy;

import java.util.List;

public class StationHours {
    private List<HoursInterval> nextIntervals;
    private String openingHours;
    private String status;

    // Getters
    public List<HoursInterval> getNextIntervals() {
        return nextIntervals;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getStatus() {
        return status;
    }
}
