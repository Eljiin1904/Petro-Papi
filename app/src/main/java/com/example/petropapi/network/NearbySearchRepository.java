package com.example.petropapi.network;

import com.example.petropapi.models.NearbySearchResponse;
import retrofit2.Call;

public class NearbySearchRepository {
    private static final String PLACE_TYPE_GAS_STATION = "gas_station";
    private final NearbySearchService service;

    public NearbySearchRepository() {
        this(RetrofitClientNearby.getRetrofitInstance().create(NearbySearchService.class));
    }

    public NearbySearchRepository(NearbySearchService service) {
        this.service = service;
    }

    public Call<NearbySearchResponse> getNearbyGasStations(double lat, double lng, int radius, String apiKey) {
        String location = lat + "," + lng;
        return service.getNearbyGasStations(location, radius, PLACE_TYPE_GAS_STATION, apiKey);
    }
}
