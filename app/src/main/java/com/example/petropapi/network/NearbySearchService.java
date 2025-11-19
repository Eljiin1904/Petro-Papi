package com.example.petropapi.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * If not used, remove it. Otherwise define the model for the response:
 */
public interface NearbySearchService {

    @GET("maps/api/place/nearbysearch/json")
    Call<NearbySearchStubResponse> getNearbyGasStations(
            @Query("location") String location,  // e.g. "lat,lng"
            @Query("radius") int radius,         // in meters
            @Query("type") String type,          // "gas_station"
            @Query("key") String key
    );
}

/**
 * Minimal placeholder so it compiles:
 */
class NearbySearchStubResponse {
    // If you want real fields, define them here. E.g. "results", "status", etc.
}
