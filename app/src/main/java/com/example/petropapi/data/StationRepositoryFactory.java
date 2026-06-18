package com.example.petropapi.data;

public final class StationRepositoryFactory {
    private StationRepositoryFactory() {
    }

    public static StationRepository createDefaultRepository() {
        return new GasBuddyStationRepository();
    }
}
