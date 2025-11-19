package com.example.petropapi.models;

public class LatLngModel {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }

    // If you no longer need "FuelStationsResult", just remove it:
    // Or comment it out:
    /*
    public static class FuelStationsResult {
        private int total;
        private int count;
        private int limit;
        private int nextOffset;
        private List<FuelStation> stations; // <--- references old FuelStation

        // getters...
    }
    */
}
