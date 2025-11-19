package com.example.petropapi.models.gasbuddy;

public class GasBuddyStationResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private StationResults stations;

        public StationResults getStations() {
            return stations;
        }
    }
}
