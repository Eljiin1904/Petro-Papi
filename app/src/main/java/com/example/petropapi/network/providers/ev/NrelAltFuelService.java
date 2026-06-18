package com.example.petropapi.network.providers.ev;

import com.example.petropapi.network.providers.ev.model.NrelAltFuelStationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NrelAltFuelService {
    @GET("alt-fuel-stations/v1/nearest.json")
    Call<NrelAltFuelStationsResponse> getNearestStations(
            @Query("api_key") String apiKey,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("fuel_type") String fuelType,
            @Query("radius") int radius
    );

    @GET("alt-fuel-stations/v1.json")
    Call<NrelAltFuelStationsResponse> getStationsByState(
            @Query("api_key") String apiKey,
            @Query("state") String state
    );
}
