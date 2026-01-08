package com.example.petropapi.network.providers.fuel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EiaFuelPriceService {
    @GET("v2/petroleum/pri/gnd/data/")
    Call<Object> getRegionalPrices(
            @Query("api_key") String apiKey,
            @Query("frequency") String frequency,
            @Query("data[0]") String data,
            @Query("facets[product]") String product,
            @Query("facets[area]") String area
    );
}
