package com.example.petropapi.network.providers.fuel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CollectApiFuelService {
    @GET("gasPrice")
    Call<Object> getFuelPrices(
            @Header("authorization") String apiKey,
            @Query("city") String city
    );
}
