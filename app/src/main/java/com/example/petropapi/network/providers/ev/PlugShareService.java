package com.example.petropapi.network.providers.ev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlugShareService {
    @GET("locations")
    Call<Object> getLocations(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude
    );
}
