package com.example.petropapi.network.providers.fuel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TomTomFuelPriceService {
    @GET("search/2/poiSearch/{query}.json")
    Call<Object> getFuelStations(
            @Path("query") String query,
            @Query("key") String apiKey,
            @Query("lat") double latitude,
            @Query("lon") double longitude
    );
}
