package com.example.petropapi.network.providers.ev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MapboxChargeFinderService {
    @GET("search/searchbox/v1/suggest")
    Call<Object> suggestChargeStations(
            @Query("access_token") String apiKey,
            @Query("q") String query,
            @Query("proximity") String proximity
    );

    @GET("search/searchbox/v1/retrieve/{id}")
    Call<Object> retrieveChargeStation(
            @Path("id") String id,
            @Query("access_token") String apiKey
    );
}
