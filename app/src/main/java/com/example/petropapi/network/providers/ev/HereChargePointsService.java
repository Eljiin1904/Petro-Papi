package com.example.petropapi.network.providers.ev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HereChargePointsService {
    @GET("v1/chargepoints")
    Call<Object> getChargePoints(
            @Query("apiKey") String apiKey,
            @Query("at") String at,
            @Query("radius") int radius
    );
}
