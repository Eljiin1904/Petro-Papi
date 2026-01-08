package com.example.petropapi.data;

import com.example.petropapi.data.model.Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TripRepository {
    private final List<Trip> recentTrips = new ArrayList<>();

    public void saveTrip(Trip trip) {
        if (trip == null) {
            return;
        }
        recentTrips.add(trip);
    }

    public List<Trip> getRecentTrips() {
        return Collections.unmodifiableList(recentTrips);
    }
}
