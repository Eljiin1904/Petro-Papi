package com.example.petropapi.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GasBuddyRetrofitClient {
    private static final String BASE_URL = "https://www.gasbuddy.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GasBuddyApiService getService() {
        return getRetrofitInstance().create(GasBuddyApiService.class);
    }
}
