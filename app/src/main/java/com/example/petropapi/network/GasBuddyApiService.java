package com.example.petropapi.network;

import com.example.petropapi.models.gasbuddy.GasBuddyStationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GasBuddyApiService {
    @POST("graphql/")
    Call<GasBuddyStationResponse> getStationsData(@Body GraphQLRequest request);
}
