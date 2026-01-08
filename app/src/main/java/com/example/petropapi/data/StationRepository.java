package com.example.petropapi.data;

import com.example.petropapi.data.model.ChargerDetail;
import com.example.petropapi.data.model.CustomerData;
import com.example.petropapi.data.model.LiveData;
import com.example.petropapi.data.model.StationDetails;
import com.example.petropapi.data.model.StationSummary;

import java.util.List;

public interface StationRepository {
    void fetchStations(double latitude, double longitude, StationRepositoryCallback<List<StationSummary>> callback);

    void fetchStationDetails(String stationId, StationRepositoryCallback<StationDetails> callback);

    void fetchChargerDetails(String stationId, StationRepositoryCallback<List<ChargerDetail>> callback);

    void fetchLiveData(String stationId, StationRepositoryCallback<LiveData> callback);

    void fetchCustomerData(String stationId, StationRepositoryCallback<CustomerData> callback);
}
