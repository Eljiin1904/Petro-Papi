package com.example.petropapi.network.providers.ev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TomTomEvService {
    @GET("search/2/poiSearch/{query}.json")
    Call<Object> searchChargePoints(
            @Path("query") String query,
            @Query("key") String apiKey,
            @Query("lat") double latitude,
            @Query("lon") double longitude
    );

    @GET("search/2/poiDetails.json")
    Call<Object> getChargePointDetails(
            @Query("key") String apiKey,
            @Query("id") String poiId
    );
}
