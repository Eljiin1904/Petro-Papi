package com.example.petropapi.network.providers.ev;

import com.example.petropapi.network.providers.ev.model.OpenChargeMapStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface OpenChargeMapService {
    @GET("poi")
    Call<List<OpenChargeMapStation>> getStations(
            @Header("X-API-Key") String apiKey,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("distance") int distanceKm,
            @Query("distanceunit") String distanceUnit
    );

    @GET("poi")
    Call<List<OpenChargeMapStation>> getStationDetails(
            @Header("X-API-Key") String apiKey,
            @Query("chargepointid") int stationId
    );
}
