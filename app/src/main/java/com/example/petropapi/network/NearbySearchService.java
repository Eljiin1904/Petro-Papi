package com.example.petropapi.network;

import com.example.petropapi.models.NearbySearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbySearchService {

    @GET("maps/api/place/nearbysearch/json")
    Call<NearbySearchResponse> getNearbyGasStations(
            @Query("location") String location,  // e.g. "lat,lng"
            @Query("radius") int radius,         // in meters
            @Query("type") String type,          // "gas_station"
            @Query("key") String key
    );
}
