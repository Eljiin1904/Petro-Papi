package com.example.petropapi.network.providers.fuel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApifyGasBuddyService {
    @GET("v2/actor-tasks/gasbuddy-scraper/run-sync-get-dataset-items")
    Call<Object> runGasBuddyTask(
            @Query("token") String apiKey,
            @Query("lat") double latitude,
            @Query("lng") double longitude
    );
}
