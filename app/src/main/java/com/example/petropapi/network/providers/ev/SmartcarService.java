package com.example.petropapi.network.providers.ev;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SmartcarService {
    @GET("vehicles")
    Call<Object> getVehicles(@Header("Authorization") String bearerToken);

    @GET("vehicles/{id}/battery")
    Call<Object> getVehicleBattery(@Header("Authorization") String bearerToken, @Path("id") String vehicleId);

    @GET("vehicles/{id}/location")
    Call<Object> getVehicleLocation(@Header("Authorization") String bearerToken, @Path("id") String vehicleId);
}
