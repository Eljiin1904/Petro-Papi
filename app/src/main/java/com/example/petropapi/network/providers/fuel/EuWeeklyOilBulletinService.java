package com.example.petropapi.network.providers.fuel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EuWeeklyOilBulletinService {
    @GET("fuel-prices")
    Call<Object> getWeeklyBulletin();
}
