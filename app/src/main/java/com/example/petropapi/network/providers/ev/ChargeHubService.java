package com.example.petropapi.network.providers.ev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ChargeHubService {
    @GET("v2/poi")
    Call<Object> getPointsOfInterest(
            @Header("X-API-Key") String apiKey,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("distance") int distanceKm
    );
}
